package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "number_of_items")
    private Long numberOfItems;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "item_order",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderNumber, order.orderNumber) &&
                Objects.equals(date, order.date) &&
                status == order.status &&
                Objects.equals(numberOfItems, order.numberOfItems) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(items, order.items) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, date, status, numberOfItems, totalPrice, items, user);
    }
}
