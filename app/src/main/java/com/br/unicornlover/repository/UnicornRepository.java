package com.br.unicornlover.repository;

import android.content.Context;

import com.br.unicornlover.dao.UnicornDao;
import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.retrofit.API;
import com.br.unicornlover.retrofit.AppDatabase;
import com.br.unicornlover.retrofit.RetrofitRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;

public class UnicornRepository {

    private final API api;
    private final UnicornDao unicornDao;

    public UnicornRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance().create(API.class);
        AppDatabase appDatabase = RetrofitRequest.provideDatabase(context);
        unicornDao = appDatabase.unicornDao();
    }

    public void cacheUnicornList(List<Unicorn> unicorns) {
        unicornDao.deleteAll();
        unicornDao.insertAll(unicorns);
    }

    public Observable<List<Unicorn>> getUnicorns() {
        return api.getUnicorns();
    }

    public Single<List<Unicorn>> getCachedUnicorns() {
        return unicornDao.getAll();
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
