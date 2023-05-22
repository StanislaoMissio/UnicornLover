package com.br.unicornlover.retrofit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.br.unicornlover.BuildConfig;
import com.br.unicornlover.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    private static Retrofit retrofit;

    @NonNull
    public static OkHttpClient client() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client())
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.app_name)).build();
    }
}
