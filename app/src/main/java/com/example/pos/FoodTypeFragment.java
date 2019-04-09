package com.example.pos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pos.Models.Product;
import com.example.pos.Models.ProductType;

import java.util.ArrayList;



public class FoodTypeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_food_type, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        ArrayList<String> productTypeNames = (ArrayList<String>) bundle.get("productTypeNames");

        RecyclerView recyclerView = rootView.findViewById(R.id.load_items);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(productTypeNames, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.VISIBLE);

        return rootView;

    }


}
