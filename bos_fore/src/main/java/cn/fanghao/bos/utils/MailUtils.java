package cn.fanghao.bos.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

public class MailUtils {
    private static String smtp_host = "smtp.163.com";     // 网易邮箱地址
    private static String username = "argusfox@163.com"; //邮箱账号
    private static String password = "Zzz142597758";    // 授权码
    private static String from = "argusfox@163.com";     // 发邮件地址
    private static String activeUrl =
            "http://localhost:9003/bos_fore/customer_activeMail";// 激活地址

    public static void sendMail(String subject, String content, String to,
                                String activecode) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtp_host);
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent("<h3>请点击地址激活:<a href=" + activeUrl
                    + "?activecode=" + activecode + ">" + "速运快递激活地址" + activeUrl
                    + "</a></h3>", "text/html;charset=utf-8");
            Transport transport = session.getTransport();
            transport.connect(smtp_host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败...");
        }
    }

    public static void main(String[] args) {
        sendMail("测试邮件", "你好，传智播客", "itcast_search@163.com", "98765");
    }
}
