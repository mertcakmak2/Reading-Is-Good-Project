package com.project.readingisgood.model;

public class OrderStatistic {

    private String monthName;
    private int bookSize;
    private Double sumPrice;

    public OrderStatistic(String monthName, int bookSize, Double sumPrice) {
        this.monthName = monthName;
        this.bookSize = bookSize;
        this.sumPrice = sumPrice;
    }

    public OrderStatistic() {
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getBookSize() {
        return bookSize;
    }

    public void setBookSize(int bookSize) {
        this.bookSize = bookSize;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }
}
