package com.wbcoding.main.Login;

import com.wbcoding.main.User.UserModel;
import com.wbcoding.main.db.H2DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO extends LoginModel {


    boolean isLoginCorrect(String username, String password) throws SQLException {

        H2DB db = new H2DB();
        try {

            String query = "SELECT * FROM TB_USERS " +
                    "WHERE user_username='" + username + "' LIMIT 1";

            PreparedStatement pt = db.openConnection().prepareStatement(query);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                Crypt crypt = new Crypt();
                if (crypt.verifyPassword(password, rs.getString("user_password"))) {
                    UserModel.setId(rs.getInt("user_id"));
                    UserModel.setName(rs.getString("user_name"));
                    db.closeConnection();
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("isLoginCorrect() error: " + e.getMessage());
        }
        db.closeConnection();
        return false;
    }
}
