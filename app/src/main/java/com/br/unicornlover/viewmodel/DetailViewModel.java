package com.br.unicornlover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;

public class DetailViewModel extends AndroidViewModel {

    private final UnicornRepository repository;
    public LiveData<Unicorn> unicornLiveData;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        repository = new UnicornRepository();
    }


    public void getUnicornDetail(String id) {
        unicornLiveData = repository.getUnicornDetail(id);
    }

}
