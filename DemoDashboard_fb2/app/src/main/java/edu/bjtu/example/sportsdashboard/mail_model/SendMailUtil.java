package edu.bjtu.example.sportsdashboard.mail_model;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Administrator on 2017/4/10.
 */

public  class SendMailUtil {

     public SendMailUtil(String from_add,String from_psw){

        //FROM_ADD = from_add;
        //FROM_PSW = from_psw;
    }

    public String FROM_ADD ; //发送方邮箱
    public String HOST; //
    public String FROM_PSW;//发送方邮箱密码
    public static final String PORT = "587";


//    //163
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //或者465  994
//    private static final String FROM_ADD = "teprinciple@163.com";
//    private static final String FROM_PSW = "teprinciple163";
////    private static final String TO_ADD = "2584770373@qq.com";
/*
    public void send(final File file,String toAdd,String toTheme,String toContext){
        final MailInfo mailInfo = creatMail(toAdd,toTheme,toContext);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }
*/

    MailInfo mailInfo;
    //public void send(String toAdd,String toTheme,String toContext){
    public void send(MailInfo mailInfo){
        //final MailInfo mailInfo = creatMail(toAdd,toTheme,toContext);
        this.mailInfo = mailInfo;
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(SendMailUtil.this.mailInfo);
            }
        }).start();
    }

    @NonNull
    public  MailInfo creatMail(String from_add,String from_psw,String toAdd,String toTheme,String toContext) {

        FROM_ADD = from_add;
        FROM_PSW = from_psw;


        String[]  strs=FROM_ADD.split("@");
        System.out.println(FROM_ADD);
        System.out.println(strs.length);
        //HOST = "smtp." + strs[1];
        HOST = "smtp.sina.com";

        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject(toTheme); // 邮件主题
        mailInfo.setContent(toContext); // 邮件文本
        return mailInfo;
    }
}
