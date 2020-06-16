package com.xmomen.module.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xmomen.module.test.domain.dto.DTO;
import com.xmomen.module.test.domain.entity.DO;
import com.xmomen.module.test.domain.vo.VO;
import com.xmomen.module.test.wrapper.UserWrapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

/*
 * @Author:      tanxinzheng
 * @Description: wrapper domain object
 * @Date:        2020/6/14
 */
public class EntityWrapper<VO, DTO, DO> {

    private static UserWrapper userWrapper;

    public static UserWrapper build(){
        if(userWrapper == null){
            userWrapper = new UserWrapper();
        }
        return userWrapper;
    }

    public DTO do2dto(DO doObj) {
        if(doObj == null){
            return null;
        }
        DTO userDTO = new DTO();
        BeanUtils.copyProperties(doObj, userDTO);
        return userDTO;
    }

    public VO dto2vo(DTO userDTO) {
        return null;
    }

    public DTO vo2dto(VO userVO) {
        return null;
    }

    public DO dto2do(DTO userDTO) {
        return null;
    }

    public List<DTO> do2dto4List(List<DO> doObj) {
        return null;
    }

    public List<VO> dto2vo4List(List<DTO> dto) {
        return null;
    }

    public IPage<VO> dto2vo4Page(IPage<DTO> dto) {
        return null;
    }

    public IPage<DTO> do2dto4Page(IPage<DO> dto) {
        return null;
    }

    public List<DTO> vo2dto4List(List<VO> vo) {
        return null;
    }

    public List<DO> dto2do4List(List<DTO> dto) {
        return null;
    }
}
