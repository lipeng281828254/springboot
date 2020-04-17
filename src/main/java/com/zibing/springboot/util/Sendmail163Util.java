package com.zibing.springboot.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/4 1:21
 * @message：
 */
public class Sendmail163Util {

    //邮件服务器主机名
    // QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
    private static String myEmailSMTPHost = "smtp.163.com";//smtp.163.com//smtp.qq.com
    //发件人邮箱
    private static String myEmailAccount = "projectManageahoh@163.com";

    //发件人邮箱密码（授权码）
    //在开启SMTP服务时会获取到一个授权码，把授权码填在这里
    private static String myEmailPassword = "ahoh2020";//kufsrpuzrlofcbec

    /**
     *     * 邮件单发（自由编辑短信，并发送，适用于私信）
     *     *
     *     * @param toEmailAddress 收件箱地址
     *     * @param emailTitle 邮件主题
     *     * @param emailContent 邮件内容
     *     * @throws Exception
     *    
     */


    public static void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception {

        Properties p = new Properties();
        p.setProperty("mail.smtp.host", myEmailSMTPHost);
        p.setProperty("mail.smtp.port", "25");
        p.setProperty("mail.smtp.socketFactory.port", "25");
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");

        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmailAccount, myEmailPassword);
            }
        });
        session.setDebug(true);//163

        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(myEmailAccount));
        // 收件人和抄送人
        message.setRecipients(Message.RecipientType.TO, toEmailAddress);
//		message.setRecipients(Message.RecipientType.CC, MY_EMAIL_ACCOUNT);

        // 内容(这个内容还不能乱写,有可能会被SMTP拒绝掉;多试几次吧)
        message.setSubject("验证码");
        message.setContent("<h1>李总,您好;你的包裹在前台</h1>", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        System.out.println("准备发送");
        Transport.send(message);

    }


    public static void main(String[] args) {
        try {
            sendEmail("1217346173@qq.com","TEST miss you","http://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
