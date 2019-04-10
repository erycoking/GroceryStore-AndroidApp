package com.example.pos;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.Models.Product;
import com.example.pos.Models.ProductType;

import java.util.ArrayList;



public class FoodTypeFragment extends Fragment {

    private static final String TAG = "FoodTypeFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: initializing foodTypeFragment");

        View rootView = inflater.inflate(R.layout.fragment_food_type, container, false);

        Object bundle = getActivity().getIntent().getExtras().get("productTypeNames");
        System.out.println(bundle.toString());
        ArrayList<String> productTypeNames = (ArrayList<String>) bundle ;
        Log.d(TAG, "onCreateView: converted array" + productTypeNames);

        Log.d(TAG, "onCreateView: loading recycler view");
        RecyclerView recyclerView = rootView.findViewById(R.id.load_items);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(productTypeNames, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setVisibility(View.VISIBLE);

        return rootView;

    }




}
