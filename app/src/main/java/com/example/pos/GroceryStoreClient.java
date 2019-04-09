package com.example.pos;

import com.example.pos.Models.Product;
import com.example.pos.Models.ProductType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GroceryStoreClient {

    @GET("/api/producttypes")
    Call<ArrayList<ProductType>> repoForProductTypes();

    @GET("/api/products/{type}")
    Call<ArrayList<Product>> repoForProducts(@Path("type") String name);

}
