package com.br.unicornlover.viewmodel;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.br.unicornlover.model.Unicorn;
import com.br.unicornlover.repository.UnicornRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
