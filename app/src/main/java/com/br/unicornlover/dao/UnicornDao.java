package com.br.unicornlover.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.br.unicornlover.model.Unicorn;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface UnicornDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Unicorn> unicorn);

    @Query("SELECT * FROM Unicorn")
    Observable<List<Unicorn>> getAll();

}
