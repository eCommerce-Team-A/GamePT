package com.example.gamePT.global.email;

import com.example.gamePT.domain.user.entity.SiteUser;
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

        int randomNumber = (int) (Math.random() * 10000);
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

    public void sendApprove(String to, boolean approve, SiteUser siteUser) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        String subject = "[gamePT] 전문가 검증 결과";
        String content;
        if (approve) {
            content = String.format("[%s] 님의 전문가 신청이 승인되었습니다.", siteUser.getUsername());
        } else {
            content = String.format("[%s] 님의 전문가 신청이 거절되었습니다.", siteUser.getUsername());
        }

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(content, true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage); // 메일방송
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}