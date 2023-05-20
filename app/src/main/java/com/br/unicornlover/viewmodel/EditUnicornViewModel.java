package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

public class EditUnicornViewModel extends AndroidViewModel {

    private UnicornRepository repository;

    public EditUnicornViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository();
    }

    public void editUnicorn(String id, Unicorn unicorn) {
        repository.editUnicorn(id, unicorn);
    }

}
