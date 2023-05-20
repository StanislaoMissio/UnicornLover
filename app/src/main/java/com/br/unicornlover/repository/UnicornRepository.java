package com.br.unicornlover.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.retrofit.API;
import com.br.unicornlover.retrofit.RetrofitRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnicornRepository {

    private final API api;

    public UnicornRepository() {
        api = RetrofitRequest.getRetrofitInstance().create(API.class);
    }

    public LiveData<List<Unicorn>> getUnicorns() {
        final MutableLiveData<List<Unicorn>> data = new MutableLiveData<>();
        api.getUnicorns().enqueue(new Callback<List<Unicorn>>() {
            @Override
            public void onResponse(@NonNull Call<List<Unicorn>> call, @NonNull Response<List<Unicorn>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Unicorn>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public void deleteUnicorn(String id) {
        api.deleteUnicorn(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("Delete", response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }

    public void editUnicorn(Unicorn unicorn) {
        api.editUnicorn(unicorn).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<Unicorn> getUnicornDetail(String id) {
        final MutableLiveData<Unicorn> unicornDetail = new MutableLiveData<>();
        api.getUnicorn(id).enqueue(new Callback<Unicorn>() {
            @Override
            public void onResponse(@NonNull Call<Unicorn> call, @NonNull Response<Unicorn> response) {
                if (response.isSuccessful()) {
                    unicornDetail.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Unicorn> call, @NonNull Throwable t) {
                Log.d("Error", t.getLocalizedMessage());
            }
        });
        return unicornDetail;
    }

    public void createUnicorn(Unicorn unicorn) {
        api.createUnicorn(unicorn).enqueue(new Callback<Unicorn>() {
            @Override
            public void onResponse(@NonNull Call<Unicorn> call, @NonNull Response<Unicorn> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Unicorn> call, @NonNull Throwable t) {

            }
        });
    }
}
