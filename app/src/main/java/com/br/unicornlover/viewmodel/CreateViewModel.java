package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

public class CreateViewModel extends AndroidViewModel {

    private UnicornRepository repository;

    public CreateViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository();
    }

    public void createUnicorn(Unicorn unicorn) {
        repository.createUnicorn(unicorn);
    }


}
