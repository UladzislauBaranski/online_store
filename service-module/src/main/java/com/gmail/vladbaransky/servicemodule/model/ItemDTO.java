package com.gmail.vladbaransky.servicemodule.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

import static com.gmail.vladbaransky.servicemodule.constant.validation.ItemValidationMessages.*;
import static com.gmail.vladbaransky.servicemodule.constant.validation.ItemValidationRules.*;

public class ItemDTO {
    private Long id;

    @NotEmpty(message = NOT_EMPTY_ITEM_TITLE_MESSAGE)
    @Pattern(regexp = ITEM_TITLE_PATTERN, message = ITEM_TITLE_PATTERN_MESSAGE)
    private String title;
    private Long uniqueNumber;

    @NotNull(message = NOT_NULL_PRICE_MESSAGE)
    @Digits(integer = PRICE_INTEGER_PART_SIZE, fraction = PRICE_FRACTION_PART_SIZE, message = PRICE_FORMAT_MESSAGE)
    @DecimalMin(value = MIN_PRICE, inclusive = false, message = MIN_PRICE_MESSAGE)
    @DecimalMax(value = MAX_PRICE, message = MAX_PRICE_MESSAGE)
    private BigDecimal price;

    @Size(max = MAX_ITEM_SUMMARY_SIZE, message = ITEM_SUMMARY_SIZE_MESSAGE)
    private String summary;

    private OrderDTO orderDTO;

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

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(id, itemDTO.id) &&
                Objects.equals(title, itemDTO.title) &&
                Objects.equals(uniqueNumber, itemDTO.uniqueNumber) &&
                Objects.equals(price, itemDTO.price) &&
                Objects.equals(summary, itemDTO.summary) &&
                Objects.equals(orderDTO, itemDTO.orderDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, uniqueNumber, price, summary, orderDTO);
    }


}
