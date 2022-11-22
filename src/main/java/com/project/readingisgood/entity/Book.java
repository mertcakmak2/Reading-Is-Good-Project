package com.project.readingisgood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bookName;
    @OneToOne(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private Stock stock;
    private Double price;
    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders;

    public Book(long id, String bookName, Stock stock, Double price, List<Order> orders) {
        this.id = id;
        this.bookName = bookName;
        this.stock = stock;
        this.price = price;
        this.orders = orders;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", stock=" + stock +
                ", orders=" + orders +
                '}';
    }
}
