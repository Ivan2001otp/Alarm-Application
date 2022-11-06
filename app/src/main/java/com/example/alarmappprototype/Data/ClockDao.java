package com.example.alarmappprototype.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alarmappprototype.Model.ClockModel;

import java.util.List;

@Dao
public interface ClockDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    void insert(ClockModel clockModel);

    @Update
    void update(ClockModel clockModel);

    @Delete
    void delete(ClockModel clockModel);

    @Query("SELECT * FROM ClockModel ORDER BY label ASC")
    LiveData<List<ClockModel>>getAllClockModelDao();

    @Query("SELECT * FROM ClockModel WHERE ClockModel.id == :id")
    LiveData<ClockModel>getClockModelDao(int id);

    @Query("DELETE FROM ClockModel")
    void deleteAll();
}
