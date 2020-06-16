package com.xmomen.module.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tanxinzheng.framework.utils.AssertValid;
import com.google.common.collect.Lists;
import com.xmomen.module.test.BeanCopierUtils;
import com.xmomen.module.test.domain.dto.UserDTO;
import com.xmomen.module.test.domain.entity.UserDO;
import com.xmomen.module.test.domain.mapper.UserMapper;
import com.xmomen.module.test.service.UserService;
import com.xmomen.module.test.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    UserMapper userMapper;

    /**
     * 新增用户
     *
     * @param userDTO
     * @return UserResponse
     */
    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        AssertValid.notNull(userDTO, "userDTO不能为空");
        UserDO user = userDTO.toDO(UserDO.class);
        boolean isOk = save(user);
        if(!isOk){
            return null;
        }
        return user.toDTO(UserDTO.class);
    }


    /**
     * 更新用户
     *
     * @param userDTO
     * @return UserResponse
     */
    @Transactional
    @Override
    public boolean updateUser(UserDTO userDTO) {
        UserDO userDO = BeanCopierUtils.copy(userDTO, UserDO.class);
        return updateById(userDO);
    }

    /**
     * 批量新增用户
     *
     * @param user
     * @return List<UserResponse>
     */
    @Transactional
    @Override
    public List<UserDTO> createUsers(List<UserDTO> user) {
        List<UserDO> userDOList = UserWrapper.build().dto2do4List(user);
        boolean isOK = saveBatch(userDOList);
        if(!isOK){
            return Lists.newArrayList();
        }
        List<String> ids = userDOList.stream().map(UserDO::getId).collect(Collectors.toList());
        return UserWrapper.build().do2dto4List(userMapper.selectBatchIds(ids));
    }


    /**
     * 根据查询参数查询单个对象
     *
     * @param id
     * @return UserResponse
     */
    @Override
    public UserDTO findOne(String id) {
        UserDO user = getById(id);
        return BeanCopierUtils.copy(user, UserDTO.class);
    }

    /**
     * 查询用户领域分页对象
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<UserDTO> findPage(IPage<UserDO> page, QueryWrapper<UserDO> queryWrapper) {
        IPage<UserDO> responsePage = (Page<UserDO>) page(page, queryWrapper);
        return UserWrapper.build().do2dto4Page(responsePage);
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return int
     */
    @Transactional
    @Override
    public int deleteUser(List<String> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return int
     */
    @Transactional
    @Override
    public boolean deleteUser(String id) {
        return userMapper.deleteById(id) > 0;
    }
}
