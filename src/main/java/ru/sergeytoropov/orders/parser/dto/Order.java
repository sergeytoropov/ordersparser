package ru.sergeytoropov.orders.parser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import static ru.sergeytoropov.orders.parser.util.Util.*;

/**
 * @author sergeytoropov
 * @since 30.04.18
 */
public class Order {
    @JsonProperty("orderId")
    private long id;
    private long amount;
    private String currency;
    private String comment;

    public Order() {
    }

    public Order(long id, long amount, String currency, String comment) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }

    public boolean validate() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return createAttribute("id", id) +
                createAttribute("amount", amount) +
                createAttribute("comment", comment, false);
    }
}
