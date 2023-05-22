package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditUnicornViewModel extends AndroidViewModel {

    private final UnicornRepository repository;

    public EditUnicornViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository(application.getApplicationContext());
    }

    public void editUnicorn(String id, Unicorn unicorn) {
        repository
                .editUnicorn(id, unicorn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
