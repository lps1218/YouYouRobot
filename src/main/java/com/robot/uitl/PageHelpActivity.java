package com.robot.uitl;

import org.springframework.stereotype.Component;

import java.util.List;

//定义分页帮助类
public class PageHelpActivity<T> {
    private int current;//当前页
    private int count;//总条数
    private int pageNo;//总页数
    private List<T> list;//返回分页数据


    public PageHelpActivity(int current, int count, int pageNo, List<T> list) {
        this.current = current;
        this.count = count;
        this.pageNo = pageNo;
        this.list = list;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
