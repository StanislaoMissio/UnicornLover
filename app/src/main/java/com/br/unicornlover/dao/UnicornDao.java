package com.br.unicornlover.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.br.unicornlover.model.Unicorn;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface UnicornDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertAll(List<Unicorn> unicorn);

    @Query("DELETE FROM Unicorn")
    void deleteAll();

    @Query("SELECT * FROM Unicorn")
    Single<List<Unicorn>> getAll();

}
