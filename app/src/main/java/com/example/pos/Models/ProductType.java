package com.example.pos.Models;

public class ProductType {
    private String $id;
    private int ProductTypeId;
    private String Name;

    public ProductType(String $id, int productTypeId, String name) {
        this.$id = $id;
        ProductTypeId = productTypeId;
        Name = name;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getProductTypeId() {
        return ProductTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        ProductTypeId = productTypeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
