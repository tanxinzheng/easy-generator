package com.xmomen.module.test.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xmomen.module.test.ObjectWrapper;
import com.xmomen.module.test.domain.dto.UserDTO;
import com.xmomen.module.test.domain.entity.UserDO;
import com.xmomen.module.test.domain.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserWrapper implements ObjectWrapper<UserVO, UserDTO, UserDO> {

    private static UserWrapper userWrapper;

    public static UserWrapper build(){
        if(userWrapper == null){
            userWrapper = new UserWrapper();
        }
        return userWrapper;
    }

    @Override
    public UserDTO do2dto(UserDO doObj, UserDTO userDTO) {
        if(doObj == null){
            return null;
        }
        BeanUtils.copyProperties(doObj, userDTO);
        return userDTO;
    }

    @Override
    public UserVO dto2vo(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO vo2dto(UserVO userVO) {
        return null;
    }

    @Override
    public UserDO dto2do(UserDTO userDTO) {
        return null;
    }

    @Override
    public List<UserDTO> do2dto4List(List<UserDO> doObj) {
        return null;
    }

    @Override
    public List<UserVO> dto2vo4List(List<UserDTO> dto) {
        return null;
    }

    @Override
    public IPage<UserVO> dto2vo4Page(IPage<UserDTO> dto) {
        return null;
    }

    @Override
    public IPage<UserDTO> do2dto4Page(IPage<UserDO> dto) {
        return null;
    }

    @Override
    public List<UserDTO> vo2dto4List(List<UserVO> vo) {
        return null;
    }

    @Override
    public List<UserDO> dto2do4List(List<UserDTO> dto) {
        return null;
    }
}
