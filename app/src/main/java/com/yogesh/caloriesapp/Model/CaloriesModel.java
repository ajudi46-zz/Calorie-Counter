package com.yogesh.caloriesapp.Model;

public class CaloriesModel {

    private String itemName;
    private long calorieValue;
    private long finalCalorieValue;

    public CaloriesModel() {
    }

    public CaloriesModel(String itemName, long calorieValue) {
        this.itemName = itemName;
        this.calorieValue = calorieValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(long calorieValue) {
        this.calorieValue = calorieValue;
    }

    public long getFinalCalorieValue() {
        return finalCalorieValue;
    }

    public void setFinalCalorieValue(long finalCalorieValue) {
        this.finalCalorieValue = finalCalorieValue;
    }
}
