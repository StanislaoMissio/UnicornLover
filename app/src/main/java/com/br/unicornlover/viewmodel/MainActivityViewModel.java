package com.br.unicornlover.viewmodel;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    private final UnicornRepository repository;
    public MutableLiveData<List<Unicorn>> unicornList = new MutableLiveData<>();
    public MutableLiveData<List<Unicorn>> cachedUnicornList = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository(application.getApplicationContext());
    }

    public void getAllUnicorns() {
        repository
                .getUnicorns()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUnicornsObserver());
    }

    public void getCachedUnicorns() {
        repository
                .getCachedUnicorns()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Unicorn>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Unicorn> unicorns) {
                        cachedUnicornList.setValue(unicorns);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Erro", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        //TODO loading if needed
                    }
                });
    }

    public void cacheUnicornList(List<Unicorn> unicorns) {
        Completable.fromAction(() -> repository.cacheUnicornList(unicorns)).subscribeOn(Schedulers.io()).subscribe();
    }

    private Observer<List<Unicorn>> getUnicornsObserver() {
        return new Observer<List<Unicorn>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<Unicorn> unicorns) {
                unicornList.setValue(unicorns);
            }

            @Override
            public void onError(Throwable e) {
                unicornList.setValue(Collections.emptyList());
            }

            @Override
            public void onComplete() {
                //TODO loading if needed
            }
        };
    }

    public void deleteUnicorn(String id) {
        repository
                .deleteUnicorn(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void saveFile(List<Unicorn> unicorns) {
        String jsonObject = new Gson().toJson(unicorns);
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "text.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonObject.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
