package com.example.kaylee.testapplication.Adapter;

import com.example.kaylee.testapplication.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaylee on 2018/2/9.
 */

public class DayAdapter extends WheelView.WheelAdapter {
    private List<Integer> dayList=new ArrayList<>();
    public DayAdapter(List<Integer> dayList) {
        this.dayList=dayList;
    }

    public void setData(List<Integer> dayList){
        this.dayList=dayList;
        notifyDataSetChanged();
    }
    @Override
    protected int getItemCount() {
        return Integer.MAX_VALUE/2;
    }

    @Override
    protected String getItem(int index) {
        //解决有时dayList报空的错误
            return dayList.get(index%dayList.size() ) + "";
    }
}
