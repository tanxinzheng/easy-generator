package com.xmomen.module.test.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.tanxinzheng.framework.model.BaseEntity;
import lombok.Data;

import java.lang.Boolean;
import java.lang.String;
import java.util.Date;
import java.io.Serializable;

@Data
@TableName(value = "xmo_user")
public class UserDO extends BaseEntity implements Serializable {

    /** 主键 */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /** 用户名 */
    @TableField(value = "USERNAME")
    private String username;
    /** 真实姓名 */
    @TableField(value = "NICKNAME")
    private String nickname;
    /** 密码盐值 */
    @TableField(value = "SALT")
    private String salt;
    /** 密码 */
    @TableField(value = "PASSWORD")
    private String password;
    /** 邮箱 */
    @TableField(value = "EMAIL")
    private String email;
    /** 手机号码 */
    @TableField(value = "PHONE_NUMBER")
    private String phoneNumber;
    /** 头像 */
    @TableField(value = "AVATAR")
    private String avatar;
    /** 锁定 */
    @TableField(value = "DISABLE")
    private Boolean disable;
    /** 注册时间 */
    @TableField(value = "CREATED_TIME")
    private Date createdTime;
    /** 最后登录时间 */
    @TableField(value = "LAST_LOGIN_TIME")
    private Date lastLoginTime;


}
