package com.br.unicornlover.viewmodel;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivityViewModel extends AndroidViewModel {

    private final UnicornRepository repository;
    private final MutableLiveData<List<Unicorn>> _unicornList = new MutableLiveData<>();
    public LiveData<List<Unicorn>> unicornList = _unicornList;
    private final MutableLiveData<Boolean> _isDeleteSuccess = new MutableLiveData();
    public LiveData<Boolean> isDeleteSuccess = _isDeleteSuccess;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new UnicornRepository(application.getApplicationContext());
    }

    public void getAllUnicorns(boolean shouldCallFromApi) {
        _isLoading.setValue(true);
        if (shouldCallFromApi) {
            repository
                    .getUnicorns()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getUnicornsObserver());
        } else {
            repository
                    .getCachedUnicorns()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Unicorn>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<Unicorn> unicorns) {
                            _unicornList.setValue(unicorns);
                            _isLoading.setValue(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            _error.setValue("Falha ao tentar carregar dados, tente novamente mais tarde!");
                        }
                    });
        }
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
                _unicornList.setValue(unicorns);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Error", e.getLocalizedMessage());
                _error.setValue("Erro ao ler os dados, tente novamente mais tarde");
            }

            @Override
            public void onComplete() {
                _isLoading.setValue(false);
            }
        };
    }

    public void deleteUnicorn(String id) {
        repository
                .deleteUnicorn(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Erro", e.getLocalizedMessage());
                        _error.setValue("Erro ao excluir item da lista, tente novamente mais tarde!");
                    }

                    @Override
                    public void onComplete() {}
                });
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
