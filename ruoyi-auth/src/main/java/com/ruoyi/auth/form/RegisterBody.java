package com.ruoyi.auth.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody extends LoginBody {

    private String name;

    private String nickName;

    private String type;

    private String email;



    private String images;


    private String area;


    private String address;


    private String x;


    private String y;

    private String avgPrice;


}
