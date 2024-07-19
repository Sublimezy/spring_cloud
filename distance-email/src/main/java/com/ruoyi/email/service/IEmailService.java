package com.ruoyi.email.service;

import com.ruoyi.email.entity.dto.EmailDTO;

import javax.mail.MessagingException;

public interface IEmailService {
    void sendEmail(EmailDTO emailDTO) throws MessagingException;
}
