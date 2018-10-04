package com.wbcoding.main.Login;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Crypt {
    private StrongPasswordEncryptor passwordEncryptor;


    // ENCRYPT
    public String encryptPassword(String password){
        passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.encryptPassword(password);
    }


    // VERIFY PASSWORD
    boolean verifyPassword(String password, String encryptedPassword){
        passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.checkPassword(password, encryptedPassword);
    }


}
