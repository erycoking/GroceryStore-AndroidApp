package com.example.pos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pos.Models.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>{
    private static final String TAG = "ProductRecyclerViewAdap";

    ArrayList<Product> products = new ArrayList<>();
    Context mContext;

    public ProductRecyclerViewAdapter(ArrayList<Product> products, Context mContext) {
        this.products.addAll(products);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler_layout, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.product_btn.setText(products.get(position).getName() + " @ " + products.get(position).getPrice());
        holder.product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ((Button)v).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        Button product_btn;
        RelativeLayout product_layout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            product_btn = itemView.findViewById(R.id.product);
            product_layout = itemView.findViewById(R.id.product_recycler_view_layout);
        }
    }
}
