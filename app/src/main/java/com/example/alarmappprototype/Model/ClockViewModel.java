package com.example.alarmappprototype.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alarmappprototype.Base.DaoRepository;

import java.util.List;

public class ClockViewModel extends AndroidViewModel {
private static DaoRepository repository;
private final LiveData<List<ClockModel>>allModelList;

    public ClockViewModel(@NonNull Application application) {
        super(application);
        repository = new DaoRepository(application);
        allModelList = repository.allClockModelList();
    }

    public static void saveModel(ClockModel model){
        repository.insertRepo(model);
    }

    public static void updateModel(ClockModel model){
        repository.updateRepo(model);
    }

    public void deleteModel(ClockModel clockModel){
        repository.deleteRepo(clockModel);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<ClockModel>>getAllModelList(){
        return this.allModelList;
    }

    public LiveData<ClockModel>getModel(int id){
        return repository.get(id);
    }
}
