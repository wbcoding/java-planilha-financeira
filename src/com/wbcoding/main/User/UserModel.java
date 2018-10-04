package com.wbcoding.main.User;

public class UserModel {

    private static int id;
    private static String name;
    private static String username;

    UserModel() {
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserModel.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserModel.name = name;
    }

    public static String getUsername() {
        return username;
    }

    static void setUsername(String username) {
        UserModel.username = username;
    }
}
