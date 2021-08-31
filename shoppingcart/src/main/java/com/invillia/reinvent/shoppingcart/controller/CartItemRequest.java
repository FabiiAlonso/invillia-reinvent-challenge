package com.invillia.reinvent.shoppingcart.controller;

public class CartItemRequest {
    private double price;
    private String name;
    private Integer quantity = 1;

    public CartItemRequest() {
    }

    public CartItemRequest(double price, String name, Integer quantity) {
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
