package com.example.alarmappprototype;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmappprototype.Adapter.AlarmAdapter;
import com.example.alarmappprototype.Controller.AlarmReciever;
import com.example.alarmappprototype.Controller.Utils;
import com.example.alarmappprototype.Data.OnAlarmClick;
import com.example.alarmappprototype.Model.ClockModel;
import com.example.alarmappprototype.Model.ClockViewModel;
import com.example.alarmappprototype.Model.TimePickerFragment;
import com.example.alarmappprototype.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,OnAlarmClick {
private ActivityMainBinding binding;
private ClockViewModel clockViewModel;
private int HourGlobal=0, MinGlobal=0;
private String label_set;
private AlertDialog.Builder deleteDialog;
AlarmAdapter alarmAdapter;
static final int REQUEST_CODE = 77;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // getSupportActionBar().hide();

        createNotificationChannel();

        clockViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(ClockViewModel.class);


        //setting all the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerAlarm.setLayoutManager(linearLayoutManager);
        binding.recyclerAlarm.setHasFixedSize(true);

        binding.addAlarm.setOnClickListener(v->{

            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"time picker");
            //setLabelDialog.show();

        });






        clockViewModel.getAllModelList().observe(this,clockModels -> {

            alarmAdapter = new AlarmAdapter(MainActivity.this,clockModels,(OnAlarmClick) this);
            binding.recyclerAlarm.setAdapter(alarmAdapter);
            alarmAdapter.notifyDataSetChanged();

            if(alarmAdapter.getItemCount()==0){
                if(binding.animation.getVisibility()== View.GONE){
                    binding.animation.setVisibility(View.VISIBLE);
                    binding.animation.playAnimation();
                }
            }else{
                if(binding.animation.getVisibility()==View.VISIBLE){
                    binding.animation.pauseAnimation();
                    binding.animation.setVisibility(View.GONE);
                    binding.animation.cancelAnimation();
                }
            }
        });
    }

    private void createNotificationChannel() {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                CharSequence name="notificationChannel";
                String description = "Channel for alarm notification";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel("notification",name,importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.side_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.deleteAll:


                if(alarmAdapter.getItemCount()==0){
                    Toast.makeText(MainActivity.this,"Make Sure You have set an alarm",Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    Utils.setTheDialogBox(MainActivity.this,clockViewModel).show();
                    //  clockViewModel.deleteAll();

                }
                break;


        }


        return true;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        String meridian="AM";

        if(hour>=12){
            //afternoon
            meridian = "PM";
        }else if(hour>=0 && hour<12){
            meridian = "AM";
        }

        if(hour>12){
            hour = hour-12;
        }
        HourGlobal = hour;
        MinGlobal = minute;
        Log.d("tag1", "onTimeSetHour : "+hour);
        Log.d("tag1", "onTimeSetHour : "+minute);
        Log.d("tag1", "onTimeSetHour : "+meridian);


        ClockModel model = new ClockModel(hour,minute,meridian);
        //model.setSetActive(true);
        ClockViewModel.saveModel(model);
        Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT)
                .show();
    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSetAlarm(ClockModel model,boolean isSetAlarm) {
        Log.d("tag", "onSetAlarm: "+model.toString());


        AlarmManager  alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,model.getHour());
        calendar.set(Calendar.MINUTE, model.getMinutes());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


        Log.d("cas", "onCalendar: " +calendar.getTime());
        Log.d("cas", "onCalendar: " +calendar.getTimeInMillis());

        if(isSetAlarm){
            model.setSetActive(true);
            ClockViewModel.updateModel(model);


//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//           alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent) ;
           // alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


            DateFormat d = new SimpleDateFormat("aa");
            String st =  d.format(new Date());

            //both are pm
            Log.d("meridian", st+" onSetAlarm: "+model.getLabel());

                //both are label pm
                if(st.contentEquals("PM") && model.getLabel().contentEquals("PM")){
                    Log.d("meridian", "working");
                    int tempHour = model.getHour()+12;
                    tempHour = Math.abs(tempHour - LocalDateTime.now().getHour());
                    int tempMin = Math.abs(tempHour-LocalDateTime.now().getMinute());

                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                    Log.d("meridian", tempHour+" : "+tempMin);
                    Toast.makeText(MainActivity.this, "Will activate in "+tempHour+" hours, "+tempMin+" minutes.", Toast.LENGTH_SHORT).show();
                }else if(st.contentEquals("AM") && model.getLabel().contentEquals("AM")){
                    Log.d("meridian", "working am-am");

                    int tempHour;
                    tempHour = Math.abs(model.getHour() - LocalDateTime.now().getHour());
                    int tempMin = Math.abs(model.getMinutes() - LocalDateTime.now().getMinute());

                    if(model.getHour()<LocalDateTime.now().getHour() || model.getMinutes()<LocalDateTime.now().getMinute()){
                        tempHour = (12 - LocalDateTime.now().getHour())+12 + model.getHour();
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(tempHour*60*60*1000 + tempMin*60*1000),AlarmManager.INTERVAL_DAY,pendingIntent);
                        Toast.makeText(MainActivity.this, "Will activate in "+tempHour+" hours, "+tempMin+" minutes.", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("meridian", tempHour+" : "+tempMin);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                        Toast.makeText(MainActivity.this, "Will activate in "+tempHour+" hours, "+tempMin+" minutes.", Toast.LENGTH_SHORT).show();

                    }


                } else if(st.contentEquals("AM") && model.getLabel().contentEquals("PM")){
                    Log.d("meridian", "working");

                    int tempHour = (12 - LocalDateTime.now().getHour()) +  model.getHour();
                    int tempMin = Math.abs(LocalDateTime.now().getMinute() - model.getMinutes());
                    Log.d("meridian", "onAlarmSet : "+tempHour);
                    Log.d("meridian", "onSetAlarm: "+LocalDateTime.now().getHour());
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(tempHour*60*60*1000 + tempMin*60*1000),AlarmManager.INTERVAL_DAY,pendingIntent);
                    Toast.makeText(MainActivity.this, "Will activate in "+tempHour+"hours, "+tempMin+" minutes.", Toast.LENGTH_SHORT).show();

                }else if(st.contentEquals("PM") && model.getLabel().contentEquals("AM")){
                    Log.d("meridian", "working");

                    int tempHour = LocalDateTime.now().getHour() - 12 ;
                    tempHour = (12 - tempHour) + model.getHour();
                    int tempMin = Math.abs(LocalDateTime.now().getMinute() - model.getMinutes());
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(tempHour*60*60*1000 + tempMin*60*1000),AlarmManager.INTERVAL_DAY,pendingIntent);
                    Log.d("meridian", "onAlarmSet : "+tempHour);
                    Toast.makeText(MainActivity.this, "Will activate in "+tempHour+"hours, "+tempMin+" minutes", Toast.LENGTH_SHORT).show();
                }


        }else{
            model.setSetActive(false);
            Log.d("tag1", "onSetAlarm: "+calendar.getTime());

            ClockViewModel.updateModel(model);
            Log.d("tag", "onSetAlarm: un-set");
            if(alarmManager==null){
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            }
           // AlarmReciever alarmReciever = new AlarmReciever();

            alarmManager.cancel(pendingIntent);

            Toast.makeText(MainActivity.this,"Alarm Turned Off",Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void deleteAlarmBin(ClockModel model) {
            deleteDialog = new AlertDialog.Builder(this);
            deleteDialog.setTitle("Delete");
            deleteDialog.setMessage("You want to delete ?");
            deleteDialog.setIcon(R.drawable.trash);

            deleteDialog.setPositiveButton("YES", (dialogInterface, i) -> {
                clockViewModel.deleteModel(model);
                alarmAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT)
                        .show();
            });

            deleteDialog.setNegativeButton("NO", (dialogInterface, i) -> {
                Toast.makeText(MainActivity.this,"Left Intact",Toast.LENGTH_SHORT)
                        .show();
            });

            deleteDialog.show();

    }
}