package com.example.alarmappprototype.Data;

import com.example.alarmappprototype.Model.ClockModel;

public interface OnAlarmClick {

    void onSetAlarm(ClockModel model,boolean isSet);
    void deleteAlarmBin(ClockModel model);
}
