package com.dapeng.springboot.service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/4 0:32
 * @message：
 */
public class EmailService {


    public static void sendMail(String recipient,String code) {
        /*
         * 1.获取Session对象
         * 2.创建一个代表邮件的对象Message
         * 3.发送邮件Transport
         */

        //1.获取连接对象
        Properties props = new Properties();
        props.setProperty("mail.host", "localhost");
        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("281828254@qq.com","LP13lili");
            }
        });

        //2.创建邮件对象
        Message message = new MimeMessage(session);

        try {
            //设置发件人
            message.setFrom(new InternetAddress("BookCityService@book.com"));
            //设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //设置邮件标题
            message.setSubject("来自网上书城的激活邮件");
            //设置邮件内容,命令行ipconfig查看本地ip地址
            String url = "http://192.168.35.19:8080/bookCity/user_active.action?code="+code;
            message.setContent("<h1>您好，请点击下面的链接完成激活！</h1><h3><a href='" + url + "'>" + url + "</a></h3>","text/html;charset=UTF-8");
            //3.发送邮件
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        sendMail("aaa@book.com","xxwdwd");

    }


}
