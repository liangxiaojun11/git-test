package com.liang.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;


public class SendMail {
    //授权码  ：ntpbujttscrbgifj
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.qq.com");
        prop.setProperty("mail.transport.protocol","smtp");
        prop.setProperty("mail.smtp.auth","true");
        //qq邮箱还需要ssl加密，其他不需要
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);
        Session session =Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1553263751@qq.com","ntpbujttscrbgifj");
            }
        });
        //查看邮件发送情况
        session.setDebug(true);
        //发送信息对象
        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com","1553263751@qq.com","ntpbujttscrbgifj");
        //写邮箱
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("1553263751@qq.com"));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("1553263751@qq.com"));
        message.setSubject("邮件标题");
        message.setContent("<h1 style='color:red'>java:hello! 你好呀</h1>","text/html;charset=utf-8");

        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }
}
