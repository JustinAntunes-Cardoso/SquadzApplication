package com.example.squadzframes.ui.events;

import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.squadzframes.R;

import java.util.Calendar;

public class popupActivity extends DialogFragment implements View.OnClickListener {

    View view;
    TimePicker tp;
    private Calendar calendar;
    private String format = " ";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view=inflater.inflate(R.layout.activity_popup,container,false);
        Button done = (Button) view.findViewById(R.id.done_button);
        tp=(TimePicker)view.findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        setTime(view);
        done.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
        String minute;
        String hour;
        int hourInt = tp.getHour();
        int minInt = tp.getMinute();
        if (minInt < 10) {
            minute = "0" + tp.getMinute();
        }else{
            minute = String.valueOf(tp.getMinute());
        }
        if (hourInt > 12){
            hourInt -= 12;
            hour = String.valueOf(hourInt);
        }else{
            hour = String.valueOf(hourInt);
        }

        String myTime= hour + ":" + minute + " " + format;
        CreateEvent ce = (CreateEvent) getActivity();
        ce.setDate(myTime);
    }
    public void setTime(View view) {
        int hour = tp.getCurrentHour();
        int min = tp.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
    }
}