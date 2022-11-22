package com.project.readingisgood.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    public Stock(long id, long quantity, Book book) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
    }

    public Stock() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", book=" + book +
                '}';
    }
}