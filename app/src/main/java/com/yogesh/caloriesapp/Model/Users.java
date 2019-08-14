package com.yogesh.caloriesapp.Model;

public class Users {

    private String id;
    private String userEmail;
    private String userPassword;
    private  String userName;
    private String weight;
    private String height;

    public Users() {
    }

    public Users(String id, String userEmail, String userPassword, String userName, String weight, String height) {
        this.id = id;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.weight = weight;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
