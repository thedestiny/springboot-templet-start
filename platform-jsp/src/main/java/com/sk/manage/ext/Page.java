package com.sk.manage.ext;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-24 12:44 AM
 */
public class Page {


    private Integer offset = 0;

    // 总页数
    private Integer pages;

    // 当前页
    private Integer current = 1;

    // 每页条数
    private Integer pageSize = 10;




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
}
