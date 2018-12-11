package edu.bjtu.example.sportsdashboard.mail_model;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class MyAuthenticator extends Authenticator {//  认证类
    String userName = null;
    String password = null;

    public MyAuthenticator() {
    }

    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
