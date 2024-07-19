package com.ruoyi.auth.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录对象
 * 
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody
{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户来源
     */
    private String source;

}
