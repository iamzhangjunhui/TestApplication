package com.example.kaylee.testapplication.Adapter;

import com.example.kaylee.testapplication.widget.WheelView;

import java.util.List;

/**
 * Created by kaylee on 2018/2/9.
 */

public class YearAdapter extends WheelView.WheelAdapter {
    private List<Integer> yearList;
    public YearAdapter(List<Integer> yearList) {
        this.yearList=yearList;
    }

    @Override
    protected int getItemCount() {
        return yearList.size();
    }

    @Override
    protected String getItem(int index) {
        return yearList.get(index)+"";
    }
}
