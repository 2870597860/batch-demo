package com.batch.real.configurtion;


public class RangePartitionerXml {
    private String splitRootTag;
    private String filePath;

    public RangePartitionerXml(String filePath,String splitRootTag) {
        this.splitRootTag = splitRootTag;
        this.filePath = filePath;
    }

}
