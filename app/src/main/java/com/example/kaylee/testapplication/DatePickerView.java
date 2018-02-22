package com.example.kaylee.testapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kaylee.testapplication.Adapter.DayAdapter;
import com.example.kaylee.testapplication.Adapter.MonthAdapter;
import com.example.kaylee.testapplication.Adapter.YearAdapter;
import com.example.kaylee.testapplication.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaylee on 2018/2/9.
 */

public class DatePickerView extends LinearLayout {
    private WheelView wheelView1, wheelView2, wheelView3;
    private static final int YEAR_MIN = 1950;
    private static final int YEAR_MAX = 2020;

    private int year = YEAR_MIN;
    private int month = 1;
    private int day = 1;
    DayAdapter dayAdapter;
    MonthAdapter monthAdapter;
    YearAdapter yearAdapter;
    int lastDayLength=0;



    private ArrayList<Integer> yearList = new ArrayList<>(YEAR_MAX - YEAR_MIN + 1);
    private ArrayList<Integer> monthList = new ArrayList<>(12);
    private ArrayList<Integer> dayList = new ArrayList<>(31);
    private Context context;

    public DatePickerView(Context context) {
        this(context,null,0);

    }

    public DatePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DatePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    //初始化时间数据
    private void init() {
        for (int i = YEAR_MIN; i < YEAR_MAX + 1; i++) {
            yearList.add(i);
        }
        for (int i = 1; i < 13; i++) {
            monthList.add(i);
        }
        for (int i = 1; i < 32; i++) {
            dayList.add(i);
        }
        View view= LayoutInflater.from(context).inflate(R.layout.view_datepicker,null);
        wheelView1 = (WheelView) view.findViewById(R.id.wheel1);
        wheelView2 = (WheelView) view.findViewById(R.id.wheel2);
        wheelView3 = (WheelView) view.findViewById(R.id.wheel3);
        yearAdapter = new YearAdapter(yearList);
        wheelView1.setAdapter(yearAdapter);
        monthAdapter = new MonthAdapter(monthList);
        wheelView2.setAdapter(monthAdapter);
        dayAdapter = new DayAdapter(dayList);
        wheelView3.setAdapter(dayAdapter);
        setDefaultDate(2020, 12, 31);
        final LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        addView(view);
        setLayoutParams(layoutParams);

        wheelView1.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                year = YEAR_MIN + index%yearList.size();
                int days = calcDay(year, month);
                dayList = getDayList(days);
                dayAdapter.setData(dayList);
                Toast.makeText(context, "onItemSelected: " + year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
            }
        });
        wheelView2.setOnItemChangeListener(new WheelView.OnItemChangeListener() {
            @Override
            public void onItemChangeListener(int index, boolean isScrollDown) {
                int position=index+1;
                if (position%monthList.size()==1&&isScrollDown&&year<YEAR_MAX){
                    wheelView1.setCurrentItem(wheelView1.getCurrentItem()+1);
                    year++;
                }
                if (position%monthList.size()==0&&!isScrollDown&&year>YEAR_MIN){
                    wheelView1.setCurrentItem(wheelView1.getCurrentItem()-1);
                    year--;
                }
            }
        });
        wheelView2.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month = index%monthList.size() + 1;
                int days = calcDay(year, month);
                lastDayLength=dayList.size();
                dayList = getDayList(days);
                if (days<lastDayLength){
                    wheelView3.setCurrentItem(Integer.MAX_VALUE/4-Integer.MAX_VALUE/4%dayList.size()+dayList.size()-1);
                    day=dayList.size();
                }else {
                    wheelView3.setCurrentItem(wheelView3.getCurrentItem() % lastDayLength + Integer.MAX_VALUE / 4 - Integer.MAX_VALUE / 4 % dayList.size());
                    day=wheelView3.getCurrentItem() % lastDayLength+1;
                }
                dayAdapter.setData(dayList);
                Toast.makeText(context, "onItemSelected: " + year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();

            }
        });
        wheelView3.setOnItemChangeListener(new WheelView.OnItemChangeListener() {
            @Override
            public void onItemChangeListener(int index, boolean isScrollDown) {
                int position=index+1;
                if (position%dayList.size()==1&&isScrollDown){
                    wheelView2.setCurrentItem(wheelView2.getCurrentItem()+1);
                    month++;
                   if (month>monthList.size()){
                      month=month%monthList.size();
                   }
                    if (month==1){
                        year++;
                        wheelView1.setCurrentItem(wheelView1.getCurrentItem()+1);
                    }
                }
                if (position%dayList.size()==0&&!isScrollDown){
                    wheelView2.setCurrentItem(wheelView2.getCurrentItem()-1);
                    if (month==1){
                        month=12;
                    }else {
                        month--;
                    }
                    if (month==12){
                        year--;
                        wheelView1.setCurrentItem(wheelView1.getCurrentItem()-1);

                    }
                }
            }
        });
        wheelView3.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                day = index%dayList.size() + 1;
                Toast.makeText(context, "onItemSelected: " + year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int calcDay(int year, int month) {
        int days = 31;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) ? 29 : 28;
                break;
        }
        return days;
    }

    private ArrayList<Integer> getDayList(int days) {
        ArrayList<Integer> list = new ArrayList<>(days);
        for (int i = 1; i < days + 1; i++) {
            list.add(i);
        }
        return list;
    }

    //设置默认的时间
    private void setDefaultDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        wheelView1.setCurrentItem(year - YEAR_MIN);
        wheelView2.setCurrentItem(Integer.MAX_VALUE/4-Integer.MAX_VALUE/4%monthList.size()+month - 1);
        wheelView3.setCurrentItem(Integer.MAX_VALUE/4-Integer.MAX_VALUE/4%dayList.size()+day - 1);
    }

}
