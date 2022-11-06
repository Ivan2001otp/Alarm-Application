package com.example.alarmappprototype.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.alarmappprototype.Model.ClockViewModel;
import com.example.alarmappprototype.R;

public class Utils {
    public static AlertDialog.Builder setTheDialogBox(final Context context, ClockViewModel clockViewModel){
        AlertDialog.Builder alertDialogBox = new AlertDialog.Builder(context)
                .setIcon(R.drawable.alert)
                .setTitle("Warning")
                .setMessage("It will clear all your alarm schedules. Are you Sure you want to proceed ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            clockViewModel.deleteAll();
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Left Intact",Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        return alertDialogBox;
    }
}
