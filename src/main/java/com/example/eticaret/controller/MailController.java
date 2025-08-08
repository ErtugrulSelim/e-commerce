package com.example.eticaret.controller;

import com.example.eticaret.dto.MailDto;
import com.example.eticaret.service.EmailSenderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailController {
    private final EmailSenderService emailSenderService;

    public  MailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }
    @PostMapping("/mail/send")
    public String sendEmail(@RequestBody MailDto mailDto) {
        emailSenderService.sendEmail(mailDto);
        return "Email send successfully";
    }
}
