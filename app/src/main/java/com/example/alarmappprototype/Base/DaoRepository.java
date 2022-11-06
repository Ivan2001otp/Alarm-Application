package com.example.alarmappprototype.Base;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.alarmappprototype.Data.ClockDao;
import com.example.alarmappprototype.Model.ClockModel;

import java.time.Clock;
import java.util.List;

public class DaoRepository {
    private final ClockDao repoDao;
    private final  LiveData<List<ClockModel>>modelList;

    public DaoRepository(Application application){
        CLockRoomDatabase instance = CLockRoomDatabase.getInstance(application);
        repoDao = instance.clockDao();
        this.modelList = repoDao.getAllClockModelDao();
    }

    public void insertRepo(ClockModel model){
        CLockRoomDatabase.databaseWriterExecutor.execute(()->{
            repoDao.insert(model);
        });
    }

    public void updateRepo(ClockModel model){
        CLockRoomDatabase.databaseWriterExecutor.execute(()->{
            repoDao.update(model);
        });
    }

    public void deleteRepo(ClockModel model){
        CLockRoomDatabase.databaseWriterExecutor.execute(()->{
            repoDao.delete(model);
        });
    }

    public LiveData<List<ClockModel>>allClockModelList(){
        return this.modelList;
    }

    public LiveData<ClockModel>get(int id){
        return repoDao.getClockModelDao(id);
    }

    public void deleteAll(){
        CLockRoomDatabase.databaseWriterExecutor.execute(()->{
            repoDao.deleteAll();
        });
    }
}
