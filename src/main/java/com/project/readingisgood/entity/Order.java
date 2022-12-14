package com.project.readingisgood.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private OrderStatesEnum state;
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "order_books",
            joinColumns = {
                    @JoinColumn(name = "order_id", referencedColumnName = "id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(name = "book_id", referencedColumnName = "id",
                            nullable = true, updatable = true)})
    private List<Book> books = new ArrayList<>();


    public Order(long id, OrderStatesEnum state, Date createdAt, Customer customer, List<Book> books) {
        this.id = id;
        this.state = state;
        this.createdAt = createdAt;
        this.customer = customer;
        this.books = books;
    }

    public Order(OrderStatesEnum state, Customer customer) {
        this.state = state;
        this.customer = customer;
    }

    public Order() {}

    public void addBooks(List<Book> books){
        for (Book book: books) {
            this.getBooks().add(book);
            this.getBooks().stream().forEach(r -> r.setOrders(Arrays.asList(this)));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatesEnum getState() {
        return state;
    }

    public void setState(OrderStatesEnum state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state=" + state +
                ", created_at=" + createdAt +
                ", customer=" + customer +
                ", books=" + books +
                '}';
    }
}