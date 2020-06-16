package com.xmomen.module.test.domain.dto;

import com.github.tanxinzheng.framework.model.BaseModel;
import com.github.tanxinzheng.framework.validate.ValidUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "用户")
@Data
public class UserDTO extends BaseModel implements Serializable {

    /** 主键 */
    @Length(max = 32, message = "主键字符长度限制[0,32]", groups = {ValidUpdate.class})
    @ApiModelProperty(value = "主键")
    private String id;
    /** 用户名 */
    @NotBlank(message = "用户名为必填项")
    @Length(max = 30, message = "用户名字符长度限制[0,30]")
    @ApiModelProperty(value = "用户名")
    private String username;
    /** 真实姓名 */
    @NotBlank(message = "真实姓名为必填项")
    @Length(max = 50, message = "真实姓名字符长度限制[0,50]")
    @ApiModelProperty(value = "真实姓名")
    private String nickname;
    /** 密码盐值 */
    @NotBlank(message = "密码盐值为必填项")
    @Length(max = 50, message = "密码盐值字符长度限制[0,50]")
    @ApiModelProperty(value = "密码盐值")
    private String salt;
    /** 密码 */
    @NotBlank(message = "密码为必填项")
    @Length(max = 50, message = "密码字符长度限制[0,50]")
    @ApiModelProperty(value = "密码")
    private String password;
    /** 邮箱 */
    @Length(max = 30, message = "邮箱字符长度限制[0,30]")
    @ApiModelProperty(value = "邮箱")
    private String email;
    /** 手机号码 */
    @Length(max = 20, message = "手机号码字符长度限制[0,20]")
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;
    /** 头像 */
    @Length(max = 100, message = "头像字符长度限制[0,100]")
    @ApiModelProperty(value = "头像")
    private String avatar;
    /** 锁定 */
    @NotNull(message = "锁定为必填项")
    @ApiModelProperty(value = "锁定")
    private Boolean disable;
    /** 注册时间 */
    @ApiModelProperty(value = "注册时间")
    private Date createdTime;
    /** 最后登录时间 */
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

}
