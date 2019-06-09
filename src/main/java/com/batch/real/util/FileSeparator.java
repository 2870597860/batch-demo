package com.batch.real.util;

import com.batch.real.entity.TagIndex;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSeparator {
    private long fileLength;
    private String splitRootEndTag;
    private String splitRootStartTag;
    private Vector<TagIndex> blockTabList = new Vector<>();
    private long currentCenterPointer;
    static CountDownLatch countDownLatch = new CountDownLatch(2);
    static ThreadLocal<RandomAccessFile> threadLocal = new ThreadLocal<>();
    RandomAccessFile raf;
    FileChannel fileChannel;
    ExecutorService executorService;
    public static void main(String[] args) throws IOException {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors:::" +i);
        //long star = System.currentTimeMillis();
        //String path ="D:\\installProgram\\workSpace\\batch-demo\\batch-demo\\data\\securitiesDemo.xml";
        String path ="D:\\installProgram\\workSpace\\batch-demo\\batch-demo\\data\\yw\\securities_20190430.xml";
        FileSeparator fileSeparator =new FileSeparator(path,"Security");
        long star = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));

        String line ="";
        long pointer = 0;
        long start = 0;
        StringBuilder sl = new StringBuilder();
        boolean flg = false;
        boolean flgend = false;
        while((line = br.readLine())!=null){
            pointer+=line.length();
            if(line.equals(fileSeparator.splitRootStartTag)){
                start = pointer - line.length();
                flg = true;
            }
            if(flg){
                sl.append(line+"\n");
            }
            if(line.equals(fileSeparator.splitRootEndTag)){
                TagIndex tagIndex = new TagIndex();
                tagIndex.setStart(start);
                tagIndex.setEnd(pointer-line.length());
                fileSeparator.blockTabList.add(tagIndex);
                flgend = true;
            }
            /*if(flg && flgend){
                System.out.println(sl);
            }*/

        }
        System.out.println("size:"+fileSeparator.blockTabList.size());
        System.out.println("耗时"+(System.currentTimeMillis()-star));

    }
    public  FileSeparator(String fileAndPath,String splitRootTag) throws IOException//取得原文件的属性
    {
        executorService = Executors.newFixedThreadPool(2);
        this.raf = new RandomAccessFile(fileAndPath,"r");
        this.fileLength = raf.length();
        this.fileChannel = raf.getChannel();
        this.splitRootEndTag = "</" + splitRootTag+">";
        this.splitRootStartTag = "<"+splitRootTag+">";
    }
}
