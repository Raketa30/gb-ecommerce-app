package ru.geekbrains.backend.security.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Future;


@Service
@Log
public class EmailService {

    private final JavaMailSender sender;
    @Value("${client.url}")
    private String clientURL;
    @Value("${email.from}")
    private String emailFrom;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    public Future<Boolean> sendActivationEmail(String email, String username, String uuid) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            String url = clientURL + "/activate-account/" + uuid;

            String htmlMsg = String.format(
                    "Hello Dear!<br/><br/>" +
                            "You created account in \"GB Shop\": %s <br/><br/>" +
                            "<a href='%s'>%s</a><br/><br/>", username, url, "For activate account push the link ;)");
            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email);
            message.setFrom(emailFrom);
            message.setSubject("GB-Shop, Account activation required");
            message.setText(htmlMsg, true);
            sender.send(mimeMessage);
            return new AsyncResult<>(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new AsyncResult<>(false);
    }

    @Async
    public Future<Boolean> sendResetPasswordEmail(String email, String token) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            String url = clientURL + "/update-password/" + token;

            String htmlMsg = String.format(
                    "Hello Dear!<br/><br/>" +
                            "For reset password in \"GB Shop\" <br/><br/>" +
                            "<a href='%s'>%s</a><br/><br/>", url, "Push this link ;)");
            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email);
            message.setFrom(emailFrom);
            message.setSubject("GB-Shop, Password reset");
            message.setText(htmlMsg, true);
            sender.send(mimeMessage);
            return new AsyncResult<>(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new AsyncResult<>(false);
    }
}
