package com.br.unicornlover.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.br.unicornlover.dao.UnicornDao;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.retrofit.API;
import com.br.unicornlover.retrofit.AppDatabase;
import com.br.unicornlover.retrofit.RetrofitRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnicornRepository {

    private final API api;
    private final UnicornDao unicornDao;

    public UnicornRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance().create(API.class);
        AppDatabase appDatabase = RetrofitRequest.provideDatabase(context);
        unicornDao = appDatabase.unicornDao();
    }

    public LiveData<List<Unicorn>> getRoomUnicornList() {
        return unicornDao.getAll();
    }

    public LiveData<List<Unicorn>> getUnicorns() {
        final MutableLiveData<List<Unicorn>> data = new MutableLiveData<>();
        api.getUnicorns().enqueue(new Callback<List<Unicorn>>() {
            @Override
            public void onResponse(@NonNull Call<List<Unicorn>> call, @NonNull Response<List<Unicorn>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    unicornDao.insertAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Unicorn>> call, @NonNull Throwable t) {
                data.setValue(getRoomUnicornList().getValue());
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

    public void editUnicorn(String id, Unicorn unicorn) {
        api.editUnicorn(id, unicorn).enqueue(new Callback<ResponseBody>() {
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
                //TODO fluxo do app
            }

            @Override
            public void onFailure(@NonNull Call<Unicorn> call, @NonNull Throwable t) {
                //TODO fluxo do app
            }
        });
    }
}
