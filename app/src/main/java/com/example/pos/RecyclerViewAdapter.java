package com.example.pos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pos.Models.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> productTypeName = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> productTypeName, Context mContext) {
        Log.d(TAG, "RecyclerViewAdapter: from the constructor"+ productTypeName);
        this.productTypeName.clear();
        this.productTypeName.addAll(productTypeName);
        Log.d(TAG, "RecyclerViewAdapter: from the constructor"+ this.productTypeName);
        this.mContext = mContext;
        Log.d(TAG, "RecyclerViewAdapter: from the constructor"+ this.mContext.getPackageName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_type_list_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.btn.setText(productTypeName.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClicked(((Button)v).getText().toString());
            }
        });
    }
    
    public void btnClicked(String name){

        Log.d(TAG, "btnClicked: ");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        GroceryStoreClient client = retrofit.create(GroceryStoreClient.class);
        Call<ArrayList<Product>> call = client.repoForProducts(name);

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()){
                    ArrayList<Product> products = new ArrayList<>();
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    products.addAll(response.body());
                    Intent i = new Intent(mContext.getApplicationContext(), ProductActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("products", products);
                    mContext.startActivity(i);
                }else{
                    Log.d(TAG, "onResponse: "+response.errorBody().toString());
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(mContext, "failed to load Product types. try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productTypeName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private static final String TAG = "ViewHolder";
        Button btn;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder: init view holder");
            btn = itemView.findViewById(R.id.product_type);
            layout = itemView.findViewById(R.id.product_layout);
        }
    }
}
