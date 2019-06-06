package com.batch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache {
    private final static Map<String,Entity> map =new HashMap<>();
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public synchronized static void put(String key,Object obj){
        //0是永不过期
        Cache.put(key,obj,0);
    }
    public synchronized static void put(String key,Object obj,long expire){
        map.remove(key);
        if(expire >0){
            Future future = executor.schedule(()-> {
                    synchronized (Cache.class){
                        map.remove(key);
                    }
            },expire, TimeUnit.MILLISECONDS);
            map.put(key,new Entity(obj,future));
        }else{
            map.put(key,new Entity(obj,null));
        }
    }
    public synchronized static Object get(String key){
        Entity entity = map.get(key);
        return entity == null?null:entity.getValue();
    }
    public synchronized static<T> T get(String key,Class<T> tClass){
       return tClass.cast(Cache.get(key));
    }

    public synchronized static Object remove(String key){
        Entity entity = map.remove(key);
        if(null == entity){
            return null;
        }
        Future future = entity.getFuture();
        if(null != future){
            future.cancel(true);
        }
        return entity.getValue();
    }

    private  static class Entity{
        private Object value;
        private Future future;

        public Entity(Object value, Future future) {
            this.value = value;
            this.future = future;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Future getFuture() {
            return future;
        }

        public void setFuture(Future future) {
            this.future = future;
        }
    }
}
