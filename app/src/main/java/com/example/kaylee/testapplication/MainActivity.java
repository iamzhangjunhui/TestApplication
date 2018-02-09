package com.example.kaylee.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kaylee.testapplication.Adapter.DayAdapter;
import com.example.kaylee.testapplication.Adapter.MonthAdapter;
import com.example.kaylee.testapplication.Adapter.YearAdapter;
import com.example.kaylee.testapplication.widget.WheelView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private DatePickerView datePickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
