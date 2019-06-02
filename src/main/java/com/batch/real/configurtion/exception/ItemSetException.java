package com.batch.real.configurtion.exception;

public class ItemSetException extends Exception{
    private static final long serialVersionUID = 1L;

    public ItemSetException(String message, Throwable cause) {
        super(message, cause);
    }

    // 提供无参数的构造方法
    public ItemSetException() {
    }

    // 提供一个有参数的构造方法，可自动生成
    public ItemSetException(String message) {
        super(message);
    }
}
