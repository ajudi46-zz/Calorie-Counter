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


public class CalorieItemAdapter extends BaseAdapter {
    long value = 0;
    private Context context;
    public static List<CaloriesModel> caloriesModelList;
    LayoutInflater inflater;
    long values = 0;
    int count = 0;
    private OnValueChanged OnValueChanged;
    private long finalCalorie;

    public CalorieItemAdapter(@NonNull Context context, List<CaloriesModel> modelList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.caloriesModelList = modelList;
        try {
            this.OnValueChanged = ((OnValueChanged) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }


    @Override
    public int getCount() {
        return caloriesModelList.size();
    }

    @Override
    public CaloriesModel getItem(int i) {
        return caloriesModelList.get(i);
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
            convertView = this.inflater.inflate(R.layout.layout_item_list, viewGroup, false);
            holder.textViewName = (TextView) convertView.findViewById(R.id.itemName);
            holder.textViewValue = (TextView) convertView.findViewById(R.id.itemValue);
            holder.editTextItemValue = (TextView) convertView.findViewById(R.id.editTextItemValue);
            holder.minusButton = (TextView) convertView.findViewById(R.id.minusButton);
            holder.plusButton = (TextView) convertView.findViewById(R.id.plusButton);
            holder.final_value = (TextView) convertView.findViewById(R.id.final_value);


            holder.plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(" position plus " + position);

                    count = Integer.parseInt(holder.editTextItemValue.getText().toString());
                    long calValue = caloriesModelList.get(position).getCalorieValue();
                    count++;

                    holder.editTextItemValue.setText(String.valueOf(count));

                    calValue = count * calValue;
                    holder.final_value.setText(String.valueOf(calValue));
                    caloriesModelList.get(position).setFinalCalorieValue(calValue);

                    OnValueChanged.onValueChange();

                }
            });
            holder.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(" position minus " + position);

                    count = Integer.parseInt(holder.editTextItemValue.getText().toString());
                    long calValue = caloriesModelList.get(position).getCalorieValue();
                    if (count == 0) {
                        Toast.makeText(context, "Cannot be Negative", Toast.LENGTH_SHORT).show();
                    } else {
                        count--;
                        holder.editTextItemValue.setText(String.valueOf(count));
                        calValue = count * calValue;
                        holder.final_value.setText(String.valueOf(calValue));
                        caloriesModelList.get(position).setFinalCalorieValue(calValue);

//                        OnValueChanged.onValueChange(String.valueOf(finalCalorie + calValue));
                        OnValueChanged.onValueChange();

                    }
                }
            });


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CaloriesModel model = caloriesModelList.get(position);
        holder.textViewName.setText(model.getItemName());
        holder.textViewValue.setText(String.valueOf(model.getCalorieValue()));

        return convertView;
    }

    private class ViewHolder {
        TextView textViewName;
        TextView textViewValue;
        TextView minusButton;
        TextView plusButton;
        TextView editTextItemValue;
        TextView final_value;
    }

    public interface OnValueChanged {
        void onValueChange();
    }


}