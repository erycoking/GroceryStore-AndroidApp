package com.example.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.pos.Models.Product;
import com.example.pos.Models.ProductType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button btn;
    final ArrayList<String> productTypeNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: initializing android-networking library");
//        AndroidNetworking.initialize(getApplicationContext());
//        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.HEADERS);



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
        String url = "http://192.168.77.84:3000/api/producttypes";
        String baseUrl = "http://192.168.77.84:3000";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().build();
                        return chain.proceed(request);
                    }
                })
                .build();

       /* Retrofit retrofit = new Retrofit.Builder()
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
                    System.out.println(response.body());
                    List<ProductType> productTypes = response.body();

                    for(ProductType type1: productTypes){
                        productTypeNames.add(type1.getName());
                    }
                }

                Log.d(TAG, "onResponse: "+response.errorBody().toString());

            }

            @Override
            public void onFailure(Call<ArrayList<ProductType>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this, "failed to load Product types. try again later", Toast.LENGTH_SHORT).show();
            }
        });*/

        AndroidNetworking.get(url)
                .addHeaders("content-type", "application/json")
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        Type type = new TypeToken<ArrayList<ProductType>>(){}.getType();
                        ArrayList<ProductType> mProductTypes = new Gson().fromJson(response.toString(), type);
                        for(ProductType type1: mProductTypes){
                            productTypeNames.add(type1.getName());
                        }
                        System.out.println("erycoking");

                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());

                        if (anError.getErrorCode() != 0){
                            Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }

                        Toast.makeText(MainActivity.this, "failed to load Product types. try again later", Toast.LENGTH_SHORT).show();
                    }
                });
        System.out.println(productTypeNames);
        getIntent().putExtra("productTypeNames", productTypeNames);
        initializeFragment();
    }

    private void initializeFragment() {
        Log.d(TAG, "initializeRecycleView: init Fragment");
        Fragment fragment;

        fragment = new FoodTypeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.foodTypeFragment, fragment);
        transaction.commit();

    }
}
