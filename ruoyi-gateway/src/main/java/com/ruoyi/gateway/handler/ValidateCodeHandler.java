package com.ruoyi.gateway.handler;

import java.io.IOException;

import com.ruoyi.gateway.domain.CaptchaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ruoyi.common.core.exception.CaptchaException;
import com.ruoyi.common.core.web.domain.AjaxResult;

import com.ruoyi.gateway.service.ValidateCodeService;
import reactor.core.publisher.Mono;

/**
 * 验证码获取
 *
 * @author ruoyi
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(CaptchaRequest.class)
                .flatMap(captchaRequest -> {
                    String uuid = captchaRequest.getUuid();
                    AjaxResult ajax;
                    try {
                        // 使用 UUID 参数调用服务方法
                        ajax = validateCodeService.createCaptcha(uuid);
                    } catch (CaptchaException | IOException e) {
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(BodyInserters.fromValue(new AjaxResult().error(e.getMessage())));
                    }
                    return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
                });
    }
}
