package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "unique_number")
    private Long uniqueNumber;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "summary")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Long uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

/*
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(title, item.title) &&
                Objects.equals(uniqueNumber, item.uniqueNumber) &&
                Objects.equals(price, item.price) &&
                Objects.equals(summary, item.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, uniqueNumber, price, summary);
    }
}
