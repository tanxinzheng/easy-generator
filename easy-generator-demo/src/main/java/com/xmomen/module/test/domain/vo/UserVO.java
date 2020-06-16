package com.xmomen.module.test.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "xmo_user")
public class UserVO implements Serializable {

    /** 主键 */
    private String id;
    /** 用户名 */
    private String username;
    /** 真实姓名 */
    private String nickname;
    /** 密码盐值 */
    private String salt;
    /** 密码 */
    private String password;
    /** 邮箱 */
    private String email;
    /** 手机号码 */
    private String phoneNumber;
    /** 头像 */
    private String avatar;
    /** 锁定 */
    private Boolean disable;
    /** 注册时间 */
    private Date createdTime;
    /** 最后登录时间 */
    private Date lastLoginTime;


}
