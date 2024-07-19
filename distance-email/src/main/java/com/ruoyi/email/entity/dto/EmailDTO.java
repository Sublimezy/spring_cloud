package com.ruoyi.email.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private String email;

    private String uuid;


    private String emailType;

}
