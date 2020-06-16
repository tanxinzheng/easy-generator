package com.xmomen.module.test.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xmomen.module.test.domain.entity.UserDO;
import com.xmomen.module.test.domain.dto.UserDTO;

import java.util.List;


public interface UserService {

    /**
     * 新增用户
     * @param userCreate
     * @return
     */
    public UserDTO createUser(UserDTO userCreate);

    /**
     * 批量新增用户
     * @param user
     * @return List<User>
     */
    List<UserDTO> createUsers(List<UserDTO> user);

    /**
     * 更新用户
     * @param   userUpdate
     * @return
     */
    public boolean updateUser(UserDTO userUpdate);

    /**
     * 根据查询参数查询单个对象
     * @param   id
     * @return
     */
    public UserDTO findOne(String id);

    /**
     * 查询用户领域分页对象
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<UserDTO> findPage(IPage<UserDO> page, QueryWrapper<UserDO> queryWrapper);

    /**
     * 批量删除用户
     * @param  ids
     * @return int
     */
    public int deleteUser(List<String> ids);

    /**
     * 删除用户
     * @param  id
     * @return int
     */
    public boolean deleteUser(String id);

}
