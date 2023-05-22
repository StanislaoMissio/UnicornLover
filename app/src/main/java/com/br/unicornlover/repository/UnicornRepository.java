package com.br.unicornlover.repository;

import android.content.Context;
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

import io.reactivex.Observable;
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

    public Observable<List<Unicorn>> getCachedUnicorns(){
        return unicornDao.getAll();
    }

    public Observable<List<Unicorn>> getUnicorns() {
        return api.getUnicorns();
    }

    public Observable<ResponseBody> deleteUnicorn(String id) {
        return api.deleteUnicorn(id);
    }

    public Observable<ResponseBody> editUnicorn(String id, Unicorn unicorn) {
        return api.editUnicorn(id, unicorn);
    }

    public Observable<Unicorn> getUnicornDetail(String id) {
        return api.getUnicorn(id);
    }

    public Observable<Unicorn> createUnicorn(Unicorn unicorn) {
        return api.createUnicorn(unicorn);
    }
}
