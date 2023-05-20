package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private final UnicornRepository repository;
    private final LiveData<List<Unicorn>> unicorns;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository();
        unicorns = repository.getUnicorns();
    }


    public LiveData<List<Unicorn>> getUnicornsResponseLiveData() {
        return unicorns;
    }

    public void deleteUnicorn(String id) {
        repository.deleteUnicorn(id);
    }

    public void editUnicorn(String id) {

    }

}
