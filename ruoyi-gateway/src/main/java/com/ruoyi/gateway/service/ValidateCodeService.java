package com.ruoyi.gateway.service;

import java.io.IOException;

import com.ruoyi.common.core.exception.CaptchaException;
import com.ruoyi.common.core.web.domain.AjaxResult;

/**
 * 验证码处理
 *
 * @author ruoyi
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     */
    public AjaxResult createCaptcha(String uuid) throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String uuid, boolean type, String code, String emailCode) throws CaptchaException;
}
