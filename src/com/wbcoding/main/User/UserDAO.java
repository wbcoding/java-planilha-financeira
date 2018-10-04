package com.wbcoding.main.User;

import com.wbcoding.main.Login.Crypt;
import com.wbcoding.main.db.H2DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends UserModel {

    static boolean checkUsernameExists(String username) {
        try {
            H2DB db = new H2DB();
            String query = "SELECT user_username FROM tb_users WHERE user_username='" + username + "'";
            PreparedStatement pt = db.openConnection().prepareStatement(query);
            ResultSet rs = pt.executeQuery();

            if (!rs.next()) {
                db.closeConnection();
                return false;
            }
            db.closeConnection();
        } catch (Exception e) {
            System.out.println("UserDAO:checkUsernameExists => " + e.getMessage());
        }
        return true;
    }

    void createNewUser(String name, String username, String password) {
        try {
            H2DB db = new H2DB();
            Crypt crypt = new Crypt();
            String securePassword = crypt.encryptPassword(password);

            String query = "INSERT INTO tb_users (USER_USERNAME, USER_PASSWORD, USER_NAME) " +
                    "VALUES ('" + username + "', '" + securePassword + "', '" + name + "')";
            PreparedStatement ps = db.openConnection().prepareStatement(query);
            ps.execute();

            db.closeConnection();

            UserModel.setName(name);
            UserModel.setUsername(username);

        } catch (Exception e) {
            System.out.println("UserDAO:createNewUser => " + e.getMessage());
        }
    }
}
