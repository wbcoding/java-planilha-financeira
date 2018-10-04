package com.wbcoding.main.Home;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

class DateModel {


    // retorna String do mês / ano (JANEIRO / 2018)
    static String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return (monthsArray()[month] + " / " + year).toUpperCase();

    }

    static String getMonthFordataAdded(String date){
        int month = Integer.parseInt(date.substring(3, 5));
        String year = date.substring(6, 10);

        return converMonthToString(month - 1) + " / " + year;
    }

    // aumenta o mês e o ano na parte acima da tabela (FEVEREIRO / 2018)
    static String getNextMonth(String dateString) {
        ArrayList<Integer> dateInt = convertDataToInteger(dateString);
        int month = dateInt.get(0);
        int year = dateInt.get(1);

        if (month == 12) {
            month = 1;
            year += 1;
        } else {
            month += 1;
        }

        return converMonthToString(month - 1) + " / " + year;

    }

    // reduz o mês e o ano na parte acima da tabela (FEVEREIRO / 2018)
    static String getPreviousMonth(String dateString) {
        ArrayList<Integer> dateInt = convertDataToInteger(dateString);
        int month = dateInt.get(0);
        int year = dateInt.get(1);

        if (month == 1) {
            month = 12;
            year -= 1;
        } else {
            month -= 1;
        }

        return converMonthToString(month - 1) + " / " + year;
    }

    // retorna o nome dos meses em português
    private static String[] monthsArray(){
        return new String[]{"janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto",
                "setembro", "outubro", "novembro", "dezembro"};
    }

    // converte o mês em número para nome
    private static String converMonthToString(int month) {
        return (monthsArray()[month]).toUpperCase();
    }

    // converte a data (JANEIRO / 2018) para ArrayList em numeros [5, 2018]
    static ArrayList<Integer> convertDataToInteger(String monthYear) {
        ArrayList<Integer> dateInt = new ArrayList<>();
        int year = Integer.parseInt(monthYear.substring(monthYear.lastIndexOf('/') + 2));
        String month = monthYear.substring(0, monthYear.lastIndexOf("/") - 1).toLowerCase();

        dateInt.add(Arrays.asList(monthsArray()).indexOf(month) + 1);
        dateInt.add(year);

        return dateInt;
    }

    // convert date from yyyy-MM-dd to dd/MM/yyyy
    static String formatDateFromDatePicker(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date != null ? date.format(formatter) : null;
    }


}
