package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.example.pos.Models.Product;

import java.util.ArrayList;

public class product_activity extends AppCompatActivity {
    private static final String TAG = "product_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);

        init();
    }

    public void init(){
        Log.d(TAG, "init: getting items from intent");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
            ArrayList<Product> products = (ArrayList<Product>) extras.get("products");

            Log.d(TAG, "init: recycler view");
            RecyclerView recyclerView = findViewById(R.id.product_recycler_view);
            ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(products, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
