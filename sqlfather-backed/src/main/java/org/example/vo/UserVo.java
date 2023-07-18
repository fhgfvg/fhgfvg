package org.example.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: ZhaoXing

 */
@Data
public class UserVo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "userName")
    private String username;


    /**
     * 用户头像
     */
    @TableField(value = "userAvatar")
    private String useravatar;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 用户角色：user/ admin
     */
    @TableField(value = "userRole")
    private String userrole;


    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createtime;

    private static final long serialVersionUID = 1L;


}
