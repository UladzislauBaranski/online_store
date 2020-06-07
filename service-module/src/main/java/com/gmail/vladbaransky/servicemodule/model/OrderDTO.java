package com.gmail.vladbaransky.servicemodule.model;

import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gmail.vladbaransky.servicemodule.constant.validation.ItemValidationMessages.*;
import static com.gmail.vladbaransky.servicemodule.constant.validation.ItemValidationRules.*;
import static com.gmail.vladbaransky.servicemodule.constant.validation.ItemValidationRules.MAX_PRICE;

public class OrderDTO {

    private Long id;
    private Long orderNumber;
    private LocalDate date;
    private StatusEnum status;
    private Long numberOfItems;

    @Digits(integer = PRICE_INTEGER_PART_SIZE, fraction = PRICE_FRACTION_PART_SIZE, message = PRICE_FORMAT_MESSAGE)
    @DecimalMin(value = MIN_PRICE, inclusive = false, message = MIN_PRICE_MESSAGE)
    @DecimalMax(value = MAX_PRICE, message = MAX_PRICE_MESSAGE)
    private BigDecimal totalPrice;
    private UserDTO userDTO;
    private List<ItemDTO> items = new ArrayList<>();


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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) &&
                Objects.equals(orderNumber, orderDTO.orderNumber) &&
                Objects.equals(date, orderDTO.date) &&
                status == orderDTO.status &&
                Objects.equals(numberOfItems, orderDTO.numberOfItems) &&
                Objects.equals(totalPrice, orderDTO.totalPrice) &&
                Objects.equals(userDTO, orderDTO.userDTO) &&
                Objects.equals(items, orderDTO.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, date, status, numberOfItems, totalPrice, userDTO, items);
    }
}
