package lv.voop.open.tools.emailsendertester.tasks;

import lv.voop.open.tools.emailsendertester.EmailSenderTester;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public record SendEmailTasks(String recipient) implements Runnable {

    @Override
    public void run() {
        if (EmailSenderTester.getInstance().getManager().getConfigurationManager() != null) {
            var cfg = EmailSenderTester.getInstance().getManager().getConfigurationManager().getData();
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", cfg.getCredentials_host());
            properties.put("mail.smtp.port", String.valueOf(cfg.getCredentials_port()));
            properties.put("mail.smtp.ssl.enable", cfg.isCredentials_ssl());
            properties.put("mail.smtp.auth", cfg.isCredentials_auth());
            properties.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(cfg.getCredentials_username(), cfg.getCredentials_password());
                }
            });
            session.setDebug(cfg.isDebug());
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(cfg.getCredentials_username()));
                message.addRecipients(Message.RecipientType.TO, recipient);
                message.setDescription("Email Sender Tool!");
                message.setSubject("Email Sender Tester Tool!");
                message.setText("If you are reading this, that means the send request have been routed and everything seemed to be working!");
                EmailSenderTester.getInstance().getLogger().info("Sending....");
                Transport.send(message);
                EmailSenderTester.getInstance().getLogger().info("Email Sent!");
            } catch (MessagingException mex) {
                EmailSenderTester.getInstance().getLogger().severe("Failed to send a email due to an error!");
                mex.printStackTrace();
            }

        }
    }
}
