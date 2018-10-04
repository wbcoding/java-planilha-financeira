package com.wbcoding.main.Login;

public class LoginModel {

    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public LoginModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
