package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateViewModel extends AndroidViewModel {

    private final UnicornRepository repository;

    public CreateViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository(application.getApplicationContext());
    }

    public void createUnicorn(Unicorn unicorn) {
        repository.createUnicorn(unicorn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Unicorn>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Unicorn unicorn) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
