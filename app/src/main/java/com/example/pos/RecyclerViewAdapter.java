package com.example.pos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.pos.Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.core.content.ContextCompat.startActivity;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> productTypeName = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> productTypeName, Context mContext) {
        this.productTypeName = productTypeName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodyct_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.btn.setText(productTypeName.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "http://10.0.2.2:3000/api/products/"+ productTypeName.get(position);

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.77.84:3000/api")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                GroceryStoreClient client = retrofit.create(GroceryStoreClient.class);
                Call<ArrayList<Product>> call = client.repoForProducts(productTypeName.get(position));

                call.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                       if(response.isSuccessful()){
                           ArrayList<Product> products = response.body();
                           Intent i = new Intent(mContext.getApplicationContext(), product_activity.class);
                           i.putExtra("products", products);
                           mContext.startActivity(i);
                       }

                        Log.d(TAG, "onResponse: "+response.errorBody().toString());

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        Toast.makeText(mContext, "failed to load Product types. try again later", Toast.LENGTH_SHORT).show();
                    }
                });

               /* AndroidNetworking.initialize(holder.layout.getContext());
                AndroidNetworking.get(url)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Type type = new TypeToken<ArrayAdapter<Product>>(){}.getType();
                                ArrayList<Product> products = new Gson().fromJson(response.toString(), type);

                                Intent i = new Intent(mContext.getApplicationContext(), product_activity.class);
                                i.putExtra("products", products);
                                mContext.startActivity(i);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(mContext, "failed to load products. try again later", Toast.LENGTH_SHORT).show();
                            }
                        });*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button btn;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.product_type);
            layout = itemView.findViewById(R.id.product_layout);
        }
    }
}
