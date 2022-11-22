package com.project.readingisgood.model.request;

public class PageableRequestModel {

    private int page;
    private int size;

    public PageableRequestModel(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageableRequestModel() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
