package com.ruoyi.email.service.impl;

import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.email.config.MailConfig;
import com.ruoyi.email.entity.dto.EmailDTO;
import com.ruoyi.email.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.common.core.constant.EmailSubjectConstants.REGISTER;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private JavaMailSender sender; // 引入Spring Mail依赖后，会自动装配到IOC容器。用来发送邮件
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailConfig mailConfig;

    public void sendEmail(EmailDTO emailDTO) throws MessagingException {

        String subject = "默认主题";

        String type = emailDTO.getEmailType(); // 你的 type 值
        try {
            Class<?> clazz = Class.forName("com.ruoyi.common.core.constant.EmailSubjectConstants");
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (field.getType() == String.class) {
                    if (type.equals(field.getName())) {
                        subject = (String) field.get(null); // null表示静态字段
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }


        Context context = new Context();


        Random random = new Random();
        String code = String.valueOf(random.nextInt(8999) + 1000); // 验证码
        context.setVariable("code", code);
        context.setVariable("subject", subject);
        System.out.println(emailDTO.getEmail()+"邮箱的"+subject+"验证码为"+code);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailDTO.getEmail());
        helper.setFrom(mailConfig.getUsername());

        helper.setSubject(subject);
        helper.setSentDate(new Date());
        String content = templateEngine.process("emailTemplate", context);
        helper.setText(content, true);
        sender.send(message);

        String verifyKey = CacheConstants.CAPTCHA_EMAIL_CODE_KEY + emailDTO.getUuid();

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

    }
}
