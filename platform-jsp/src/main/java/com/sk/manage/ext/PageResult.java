package com.sk.manage.ext;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-12-24 12:46 AM
 */
public class PageResult<T> implements Serializable {


    private Integer offset;

    // 总页数
    private Integer pages;

    // 当前页
    private Integer current;

    // 每页条数
    private Integer pageSize;

    // 总条数
    private Integer total;

    // 数据
    private List<T> list;

    private Integer pre;

    private Integer next;

    public Integer getPre() {
        return pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public void handle() {

        this.pre = (this.current - 1 <= 1) ? 1 : this.current - 1;
        this.next = (this.current + 1 >= pages) ? pages : this.current + 1;


    }


}
