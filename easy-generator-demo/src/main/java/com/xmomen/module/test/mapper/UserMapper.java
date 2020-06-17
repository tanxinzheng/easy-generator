package com.xmomen.module.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmomen.module.test.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    int insertBatch(@Param("list") List<UserDO> list);

}
