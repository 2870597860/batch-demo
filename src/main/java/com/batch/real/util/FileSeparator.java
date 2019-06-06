package com.batch.real.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileSeparator {
    private long fileLength;
    List<>
    public void getFileAttribute(String fileAndPath) throws IOException//取得原文件的属性
    {
        RandomAccessFile raf = new RandomAccessFile(fileAndPath,"r");
        fileLength = raf.length();
    }
    public long getBlockNum(){
        long lenOne = fileLength / 2;

    }
}
