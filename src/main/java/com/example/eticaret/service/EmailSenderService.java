package com.example.eticaret.service;

import com.example.eticaret.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private JavaMailSender mailSender;

    public void sendEmail(MailDto mailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("e.selimbark@gmail.com");
        message.setTo(mailDto.getToEmail());
        message.setText(mailDto.getText());
        message.setSubject(mailDto.getSubject());
        mailSender.send(message);

        System.out.println("Mail sent successfully");
    }

}
