package com.ruitu.arad.api;

import java.io.Serializable;
import java.util.List;

/**
 * 分页bean
 */
public class PageModel<T> implements Serializable {

    public List<T> list;

    public int pageNum;//当前页码
    public int pageSize;//每页的数量
    public int size;//当前页的数量(最后一页很可能数量少)
    public int pages;//总页数
    public int total;//数据总条数

    @Override
    public String toString() {
        return "PageModel{" +
                "totalPage=" + pages +
                ", dataList=" + list +
                '}';
    }

    public int getTotalPage() {
        return pages;
    }

    public List<T> getDataList() {
        return list;
    }

    public int getCurrentPage() {
        return pageNum;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {//数据总条数
        return total;
    }
}
