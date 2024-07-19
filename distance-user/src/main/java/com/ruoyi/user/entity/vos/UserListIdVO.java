package com.ruoyi.user.entity.vos;

import com.ruoyi.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListIdVO {


    /** 用户ID */
    private Long userId;

    /** 用户账号 */
    private String userName;

}
