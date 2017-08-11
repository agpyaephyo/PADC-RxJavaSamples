package xyz.aungpyaephyo.padc.rxjava;

import android.app.Application;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.aungpyaephyo.padc.rxjava.network.RestaurantsApi;
import xyz.aungpyaephyo.padc.rxjava.utils.RxJavaSamplesConstants;

/**
 * Created by aung on 8/11/17.
 */

public class RxJavaApp extends Application {

    public static final String TAG = "RxJavaApp";

    private RestaurantsApi theApi;

    @Override
    public void onCreate() {
        super.onCreate();
        initRestaurantApi();
    }

    public RestaurantsApi getRestaurantApi() {
        return theApi;
    }

    private void initRestaurantApi() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RxJavaSamplesConstants.RESTAURANTS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(RestaurantsApi.class);
    }
}
