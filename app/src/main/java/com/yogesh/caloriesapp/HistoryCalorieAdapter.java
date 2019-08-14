package com.yogesh.caloriesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yogesh.caloriesapp.Model.CaloriesModel;

import java.util.List;


public class HistoryCalorieAdapter extends BaseAdapter {
    long value = 0;
    private Context context;
    public static List<CaloriesModel> historyCaloriesModelList;
    LayoutInflater inflater;
    long values = 0;
    int count = 0;
    private long finalCalorie;

    public HistoryCalorieAdapter(@NonNull Context context, List<CaloriesModel> modelList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.historyCaloriesModelList = modelList;
    }


    @Override
    public int getCount() {
        return historyCaloriesModelList.size();
    }

    @Override
    public CaloriesModel getItem(int i) {
        return historyCaloriesModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.layout_item_history, viewGroup, false);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.txt_view_his_item);
            holder.textViewCalorieValue = (TextView) convertView.findViewById(R.id.txt_view_his_calorie);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CaloriesModel model = historyCaloriesModelList.get(position);
        holder.textViewDate.setText(model.getItemName());
        holder.textViewCalorieValue.setText(String.valueOf(model.getCalorieValue()));

        return convertView;
    }

    private class ViewHolder {
        TextView textViewDate;
        TextView textViewCalorieValue;
    }

}