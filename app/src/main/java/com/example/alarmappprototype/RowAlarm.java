package com.example.alarmappprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alarmappprototype.databinding.ActivityRowAlarmBinding;

public class RowAlarm extends AppCompatActivity {
private ActivityRowAlarmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRowAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}