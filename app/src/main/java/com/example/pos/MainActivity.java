package com.example.pos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.pos.Models.ProductType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button btn;
    final ArrayList<String> productTypeNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: initializing the UI");
        init();
    }

    public void init(){
        Log.d(TAG, "init: Initializing");
        btn = findViewById(R.id.shop_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFoodTypes();
            }
        });

    }

    public void loadFoodTypes(){
        Log.d(TAG, "loadFoodTypes: loading product types");
        String baseUrl = "http://10.0.2.2:3000/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GroceryStoreClient client = retrofit.create(GroceryStoreClient.class);
        Call<ArrayList<ProductType>> call = client.repoForProductTypes();


        call.enqueue(new Callback<ArrayList<ProductType>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductType>> call, Response<ArrayList<ProductType>> response) {
                if(response.isSuccessful()){
                    System.out.println("erycoking");
                    final ArrayList<ProductType> productTypes = new ArrayList<>();
                    productTypes.addAll(response.body());
                    System.out.println(productTypes);

                    for(ProductType type1: productTypes){
                        System.out.println(type1);
                        productTypeNames.add(type1.getName());
                    }

                    System.out.println(productTypeNames);
                    getIntent().putExtra("productTypeNames", productTypeNames);
                    initializeFragment();
                }else{
                    Log.d(TAG, "onResponse: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductType>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this, "failed to load Product types. try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeFragment() {
        Log.d(TAG, "initializeRecycleView: init Fragment");
        Fragment fragment;

        fragment = new FoodTypeFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.foodTypeFragment, fragment);
        transaction.commit();

    }
}
