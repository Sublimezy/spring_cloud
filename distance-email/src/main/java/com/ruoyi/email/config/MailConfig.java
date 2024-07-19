package com.ruoyi.email.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
public class MailConfig {
    
    @Value("${spring.mail.host}")
    private String host;
    
    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.password}")
    private String password;
    
    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;


}
