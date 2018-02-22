package com.example.kaylee.testapplication.Adapter;

import com.example.kaylee.testapplication.widget.WheelView;

import java.util.List;

/**
 * Created by kaylee on 2018/2/9.
 */

public class MonthAdapter extends WheelView.WheelAdapter {
    private List<Integer> monthList;
    public MonthAdapter(List<Integer> monthList) {
        this.monthList=monthList;
    }

    @Override
    protected int getItemCount() {
        return Integer.MAX_VALUE/2;
    }

    @Override
    protected String getItem(int index) {
        return monthList.get(index%monthList.size())+"月";
    }
}
