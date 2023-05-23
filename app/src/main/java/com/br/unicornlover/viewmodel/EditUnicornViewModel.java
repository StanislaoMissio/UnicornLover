package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class EditUnicornViewModel extends AndroidViewModel {

    private final UnicornRepository repository;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<Boolean> _isSuccess = new MutableLiveData<>();
    public LiveData<Boolean> isSuccess = _isSuccess;
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public EditUnicornViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository(application.getApplicationContext());
    }

    public void editUnicorn(String id, Unicorn unicorn) {
        _isLoading.setValue(true);
        repository
                .editUnicorn(id, unicorn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        _isSuccess.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        _error.setValue("Erro ao tentar atualizar os dados, tente novamente mais tarde!");
                        _isSuccess.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        _isLoading.setValue(false);
                    }
                });
    }

}
