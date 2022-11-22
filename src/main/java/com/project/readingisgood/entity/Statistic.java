package com.project.readingisgood.entity;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String monthName;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;

    public Statistic(long id, String monthName, int totalOrderCount, int totalBookCount, double totalPurchasedAmount) {
        this.id = id;
        this.monthName = monthName;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public Statistic(String monthName, int totalOrderCount, int totalBookCount, double totalPurchasedAmount) {
        this.monthName = monthName;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public Statistic() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(int totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public double getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(double totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }
}
