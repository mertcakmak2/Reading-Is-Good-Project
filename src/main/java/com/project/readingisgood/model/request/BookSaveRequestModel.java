package com.project.readingisgood.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class BookSaveRequestModel {

    @NotEmpty(message = "Book name field cannot be empty")
    private String bookName;
    @Min(value = 0, message = "Quantity cannot be less than 0")
    private long quantity;
    @Min(value = 1, message = "Price cannot be less than 1")
    private double price;

    public BookSaveRequestModel(String bookName, long quantity, double price) {
        this.bookName = bookName;
        this.quantity = quantity;
        this.price = price;
    }

    public BookSaveRequestModel(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
