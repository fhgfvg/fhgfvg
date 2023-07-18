package org.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: ZhaoXing

 */
@Data
public class UserDto implements Serializable {

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;

    /**
     * 二次验证
     */
    private String checkpassword;

    private static final long serialVersionUID = 1L;

}
