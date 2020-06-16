package com.xmomen.module.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.tanxinzheng.framework.mybatis.domian.QueryParams;
import com.xmomen.module.test.domain.entity.UserDO;
import com.xmomen.module.test.domain.dto.UserDTO;
import com.xmomen.module.test.domain.vo.UserVO;
import com.xmomen.module.test.service.UserService;
import com.xmomen.module.test.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@Api(tags = "用户接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 用户列表
     * @param queryParams
     * @return
     */
    @ApiOperation(value = "分页查询用户")
    @GetMapping
    
    public IPage<UserVO> selectPage(QueryParams<UserDO> queryParams){
        IPage<UserDTO> page = userService.findPage(queryParams.getPage(), queryParams.getQueryWrapper());
        return UserWrapper.build().dto2vo4Page(page);
    }

    /**
     * 查询单个用户
     * @param   id  主键
     * @return  UserResponse   用户领域对象
     */
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/{id}")
    public UserDTO selectOne(@PathVariable(value = "id") String id){
        return userService.findOne(id);
    }

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "新增用户")
    @PostMapping
    public UserVO create(@RequestBody @Valid UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);
        return UserWrapper.build().dto2vo(userDTO);
    }

    /**
     * 更新用户
     * @param id    主键
     * @param userVO  更新对象参数
     * @return  UserResponse   用户领域对象
     */
    @ApiOperation(value = "更新用户")
    @PutMapping(value = "/{id}")
    public boolean update(@PathVariable(value = "id") String id,
                              @RequestBody @Valid UserDTO userDTO){
        if(StringUtils.isNotBlank(id)){
            userDTO.setId(id);
        }
        return userService.updateUser(userDTO);
    }

    /**
     *  删除用户
     * @param id    主键
     */
    @ApiOperation(value = "删除单个用户")
    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable(value = "id") String id){
        return userService.deleteUser(id);
    }

    /**
     *  删除用户
     * @param ids    查询参数对象
     */
    @ApiOperation(value = "批量删除用户")
    @DeleteMapping
    public int batchDelete(@RequestBody List<String> ids){
        return userService.deleteUser(ids);
    }


}
