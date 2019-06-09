package com.batch.real.entity;

/**
 * Created by Administrator on 2019/6/6.
 */
public class TagIndex {
    private long start;
    private long end;
    private String tagName;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "TagIndex{" +
                "start=" + start +
                ", end=" + end +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
