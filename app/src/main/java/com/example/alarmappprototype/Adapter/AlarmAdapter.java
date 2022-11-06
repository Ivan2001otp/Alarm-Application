package com.example.alarmappprototype.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmappprototype.Data.OnAlarmClick;
import com.example.alarmappprototype.MainActivity;
import com.example.alarmappprototype.Model.ClockModel;
import com.example.alarmappprototype.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private final Context context;
    private final List<ClockModel>AllAlarmlist;
    private final OnAlarmClick onAlarmClick;

    public AlarmAdapter(Context context, List<ClockModel>list, OnAlarmClick onAlarmClick){
        this.context = context;
        this.AllAlarmlist = list;
        this.onAlarmClick = onAlarmClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("tag", "inflating exe");
        View view = LayoutInflater.from(context).inflate(R.layout.todo_row_alarm,parent,false);
        Log.d("tag", "inflating term");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Clock

        int hour = AllAlarmlist.get(position).getHour();
        int minutes = AllAlarmlist.get(position).getMinutes();
        String label = AllAlarmlist.get(position).getLabel();

        String HR = Integer.toString(hour);
        String MIN = Integer.toString(minutes);

        switch (HR){
            case "0":
                HR = "12";
                break;

            case "1":
                HR = "01";
                break;
            case "2":
                HR = "02";
                break;
            case "3":
                HR = "03";
                break;
            case "4":
                HR = "04";
                break;
            case "5":
                HR = "05";
                break;
            case "6":
                HR = "06";
                break;
            case "7":
                HR = "07";
                break;
            case "8":
                HR = "08";
                break;
            case "9":
                HR = "09";
                break;

        }


        switch (MIN){
            case "0":
                MIN = "00";
                break;
            case "1":
                MIN = "01";
                break;
            case "2":
                MIN = "02";
                break;
            case "3":
                MIN = "03";
                break;
            case "4":
                MIN = "04";
                break;
            case "5":
                MIN = "05";
                break;
            case "6":
                MIN = "06";
                break;
            case "7":
                MIN = "07";
                break;
            case "8":
                MIN = "08";
                break;
            case "9":
                MIN = "09";
                break;

        }


        holder.hour_tv.setText(HR);
        holder.minute_tv.setText(MIN);
        holder.label_tv.setText(label);

        if(AllAlarmlist.get(position).isSetActive()){
            holder.setAlarm.setChecked(true);
        }else{
            holder.setAlarm.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        Log.d("tag", "recyclerAdapter size returned");
        return AllAlarmlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView hour_tv;
        private TextView minute_tv;
        private TextView label_tv;
        Switch setAlarm;
        ImageView deleteAlarm;

        OnAlarmClick onAlarmClickInstance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("tag", "holder exe");

            this.onAlarmClickInstance = onAlarmClick;
            hour_tv = itemView.findViewById(R.id.row_hour);
            minute_tv = itemView.findViewById(R.id.row_minute);
            label_tv = itemView.findViewById(R.id.meridian);
            setAlarm = itemView.findViewById(R.id.setAlarmSwitch);
            deleteAlarm = itemView.findViewById(R.id.deleteBtn);

            Log.d("tag", "holder terminated");

            deleteAlarm.setOnClickListener(this);
            setAlarm.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            ClockModel model = AllAlarmlist.get(getAdapterPosition());
            boolean isSet = false;
                switch (view.getId()){
                    case R.id.deleteBtn:
                        onAlarmClickInstance.deleteAlarmBin(model);
                    case R.id.setAlarmSwitch:
                        onAlarmClickInstance.onSetAlarm(model,setAlarm.isChecked());
                }
        }
    }
}
