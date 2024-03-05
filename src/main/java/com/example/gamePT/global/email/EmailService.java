package com.example.gamePT.global.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public int sendConfirm(String to) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        int randomNumber = (int) (Math.random()*10000);
        String subject = "[gamePT] 임시 비밀번호 입니다..";
        String content = "임시 비밀번호 발급 [" + randomNumber + "]";

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(content, true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage); // 메일방송
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return randomNumber;
    }

}