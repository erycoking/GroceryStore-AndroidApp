package com.example.pos.Models;

import java.io.Serializable;

public class Product implements Serializable {
    private String $id;
    private int ProductId;
    private String Name;
    private double Price;
    private String Type;

    public Product(String $id, int productId, String name, double price, String type) {
        this.$id = $id;
        ProductId = productId;
        Name = name;
        Price = price;
        Type = type;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "$id='" + $id + '\'' +
                ", ProductId=" + ProductId +
                ", Name='" + Name + '\'' +
                ", Price=" + Price +
                ", Type='" + Type + '\'' +
                '}';
    }
}
