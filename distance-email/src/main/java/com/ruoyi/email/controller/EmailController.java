package com.ruoyi.email.controller;

import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.email.config.MailConfig;
import com.ruoyi.email.entity.dto.EmailDTO;
import com.ruoyi.email.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;


import static com.ruoyi.common.core.web.domain.AjaxResult.error;
import static com.ruoyi.common.core.web.domain.AjaxResult.success;

@RestController
@RequestMapping("/email")
public class EmailController {


    @Autowired
    private RedisService redisService;

    @Autowired
    private IEmailService emailService;

    //发送验证码
    @PostMapping("/sendEmail")
    public AjaxResult sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException {

        try {

            String verifyKey = CacheConstants.CAPTCHA_EMAIL_CODE_KEY + emailDTO.getUuid();

            String captcha = redisService.getCacheObject(verifyKey);
            if (StringUtils.isNotEmpty(captcha))
            {
                return error("验证码发送频繁，请稍后再试！");
            }

            emailService.sendEmail(emailDTO);
            return success("成功发送邮箱验证码");
        } catch (MessagingException e) {
            e.printStackTrace();
            return error("发送失败");
        }
    }


}





