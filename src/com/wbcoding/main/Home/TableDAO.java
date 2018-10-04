package com.wbcoding.main.Home;

import com.wbcoding.main.db.H2DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


class TableDAO {

    // Obter lista do mês atual ao abrir o programa
    ObservableList<TableModel> getCurrentMonthData(int userId) {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        String currentMonth = month < 10 ? ("0" + month) : ("" + month);
        String currentMonthYear = String.format("%s/%d", currentMonth, c.get(Calendar.YEAR));
        String query = "SELECT * FROM TB_ACCOUNTS WHERE user_id = " + userId + " " +
                "AND AC_DATE LIKE '%" + currentMonthYear + "%' ORDER BY AC_DATE DESC";
        return getItens(query);
    }


    // pegar os valores e separar por tipo de conta
    ArrayList<String> showValuesByAccount(int userId) {
        ArrayList<String> data = new ArrayList<>();
        double chequing = 0, savings = 0, creditCard = 0, money = 0;

        try {
            H2DB db = new H2DB();
            String query = "SELECT ac_value, ac_account FROM tb_accounts WHERE USER_ID = " + userId + "";
            PreparedStatement ps = db.openConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("ac_account").equals("corrente")) {
                    chequing += rs.getDouble("ac_value");
                }
                if (rs.getString("ac_account").equals("poupança")) {
                    savings += rs.getDouble("ac_value");
                }
                if (rs.getString("ac_account").equals("cartão de crédito")) {
                    creditCard += rs.getDouble("ac_value");
                }
                if (rs.getString("ac_account").equals("dinheiro")) {
                    money += rs.getDouble("ac_value");
                }
            }

            db.closeConnection();
        } catch (Exception e) {
            System.out.println("TableDao:showValuesByAccount => " + e.getMessage());
        }

        double total = chequing + savings + creditCard + money;

        String chequingStr = chequing >= 0 ? "R$" + roundOff(chequing) : "- R$" + roundOff(Math.abs(chequing));
        String savingsStr = savings >= 0 ? "R$" + roundOff(savings) : "- R$" + roundOff(Math.abs(savings));
        String creditCardStr = creditCard >= 0 ? "R$" + roundOff(creditCard) : "- R$" + roundOff(Math.abs(creditCard));
        String moneyStr = money >= 0 ? "R$" + roundOff(money) : "- R$" + roundOff(Math.abs(money));
        String totalStr = total >= 0 ? "R$" + roundOff(total) : "- R$" + roundOff(Math.abs(total));

        data.add(chequingStr);
        data.add(savingsStr);
        data.add(creditCardStr);
        data.add(moneyStr);
        data.add(totalStr);
        return data;
    }


    private String roundOff(double val) {
        DecimalFormat f = new DecimalFormat("0.00");
        return f.format(val);
    }


    // Obter lista do próximo mês
    ObservableList<TableModel> getPreviousNextMonthData(String monthYear, int userId) {
        ArrayList arrayDate = DateModel.convertDataToInteger(monthYear);
        String month = (int) arrayDate.get(0) < 10 ? "0" + arrayDate.get(0) : arrayDate.get(0) + "";
        int year = (int) arrayDate.get(1);
        String monthYearString = month + "/" + year;
        String query = "SELECT * FROM TB_ACCOUNTS WHERE user_id = " + userId + " " +
                "AND AC_DATE LIKE '%" + monthYearString + "%' ORDER BY AC_DATE DESC";
        return getItens(query);
    }


    // retorna os itens da tabela
    private ObservableList<TableModel> getItens(String query) {
        ObservableList<TableModel> itens = FXCollections.observableArrayList();
        try {
            H2DB db = new H2DB();

            PreparedStatement ps = db.openConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ac_id");
                String description = rs.getString("ac_description");
                String value = roundOff(Double.parseDouble(rs.getString("ac_value")));
                String date = rs.getString("ac_date");
                String account = rs.getString("ac_account");

                itens.add(new TableModel(id, description, account, value, date));
            }

            db.closeConnection();
        } catch (Exception e) {
            System.out.println("TableDao:getCurrentMonthData => " + e.getMessage());
        }
        return itens;
    }

    // insere dados na tabela
    String insertNewData(int userId, String description, String value, String date, String operation, String account) {
        if (description.equals("") || value.equals("") || date == null) {
            return "Preencha os campos corretamente.";
        }

        try {
            double valueDouble = Double.parseDouble(value);
            valueDouble = operation.equals("+") ? valueDouble : -(valueDouble);


            H2DB db = new H2DB();
            String query = "INSERT INTO TB_ACCOUNTS (USER_ID, AC_DESCRIPTION, AC_VALUE, AC_DATE, AC_ACCOUNT) " +
                    "VALUES (" + userId + ", '" + description + "', " + valueDouble + ", '" + date + "', '" + account + "')";
            PreparedStatement ps = db.openConnection().prepareStatement(query);
            ps.execute();

            db.closeConnection();

            return "Dados inseridos com sucesso.";

        } catch (Exception e) {
            System.out.println("TableDao:insertNewData => " + e.getMessage());
            return "Não foi possível inserir os dados.";
        }
    }

    // retorna a lista com os itens do mês do ultimo item adicionado
    ObservableList<TableModel> showMonthWithDataAdded(String monthYear, int userId) {
        String query = "SELECT * FROM TB_ACCOUNTS WHERE user_id = " + userId + " " +
                "AND AC_DATE LIKE '" + monthYear + "%' ORDER BY AC_DATE DESC";
        return getItens(query);
    }


    boolean deleteTableRow(int id) {
        try {
            H2DB db = new H2DB();
            String query = "DELETE FROM TB_ACCOUNTS WHERE AC_ID=" + id + " LIMIT 1";
            PreparedStatement ps = db.openConnection().prepareStatement(query);
            ps.execute();

            db.closeConnection();
            return true;

        } catch (Exception e) {
            System.out.println("TableDao:insertNewData => " + e.getMessage());
        }

        return false;
    }

}


