package com.br.unicornlover.retrofit;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.br.unicornlover.dao.UnicornDao;
import com.br.unicornlover.model.Unicorn;

@Database(entities = {Unicorn.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnicornDao unicornDao();
}
