package com.robot.uitl;

import java.util.List;

public class PageHelp<T> {
    private Integer index;
    private Integer count;
    private Integer pageCount;
    private List<T> T;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getT() {
        return T;
    }

    public void setT(List<T> t) {
        T = t;
    }

    public PageHelp(Integer index, Integer count, Integer pageCount, List<T> t) {
        this.index = index;
        this.count = count;
        this.pageCount = pageCount;
        T = t;
    }
}
