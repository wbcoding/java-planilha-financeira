package com.wbcoding.main.db;

import javafx.scene.control.Alert;

import java.sql.*;

public class H2DB {

    private Connection conn;

    public H2DB() {

        try {
            String url;
            if(System.getProperty("os.name").startsWith("Mac")) {
                url = "jdbc:h2:"+System.getProperty("user.dir")+"/db/sql";
            }else{
                url= "jdbc:h2:file:C:\\Controle_financeiro\\db\\sql";
            }

            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url, "", "");
            System.out.println("H2DB Connected!");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DB connection error => " + e.getMessage());
            alert.showAndWait();
        }
    }


    public Connection openConnection(){
        return conn;
    }

    public void closeConnection() throws SQLException{
        conn.close();
        System.out.println("H2DB Disconnected!");
    }

    public static void createDBSchema(){
        try{
            H2DB db = new H2DB();
            ResultSet rs = db.openConnection().getMetaData().getTables(
                    null, null, "TB_ACCOUNTS", null);
            if (!rs.next()) {
                String userQuery = "CREATE TABLE TB_ACCOUNTS(" +
                    "AC_ID int auto_increment primary key, " +
                    "USER_ID        INTEGER, " +
                    "AC_DESCRIPTION VARCHAR(300), " +
                    "AC_VALUE       VARCHAR(50), " +
                    "AC_DATE        VARCHAR(50), " +
                    "AC_ACCOUNT     VARCHAR(50))";
                PreparedStatement ps = db.openConnection().prepareStatement(userQuery);
                ps.execute();

                String accountQuery = "create table TB_USERS(" +
                        "USER_ID int auto_increment primary key, " +
                        "USER_USERNAME VARCHAR(100)," +
                        "USER_PASSWORD VARCHAR(300)," +
                        "USER_NAME     VARCHAR(200)" +
                        ");";
                ps = db.openConnection().prepareStatement(accountQuery);
                ps.execute();
            }

            db.closeConnection();

        }catch (Exception e){
            System.out.println("H2DB:createDBSchema => " + e.getMessage());
        }

    }
}
