package com.example.alarmappprototype.Base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.alarmappprototype.Data.ClockDao;
import com.example.alarmappprototype.Model.ClockModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ClockModel.class},version = 1,exportSchema = false)
public abstract class CLockRoomDatabase extends RoomDatabase {
    public static final int  NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "ClockModel";
    public static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static volatile CLockRoomDatabase INSTANCE;


    public abstract ClockDao clockDao();

    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriterExecutor.execute(()->{
                //write here.
                ClockDao clock_Dao = INSTANCE.clockDao();

                //clean slate
                clock_Dao.deleteAll();

            });
        }
    };


    public static CLockRoomDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (CLockRoomDatabase.class){
                if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        CLockRoomDatabase.class,
                                            DATABASE_NAME)
                                        .addCallback(sRoomDatabaseCallback)
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
