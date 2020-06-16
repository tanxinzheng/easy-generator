package com.xmomen.module.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface ObjectWrapper<VO, DTO, DO> {

    DTO do2dto(DO doObj, DTO dto);

    VO dto2vo(DTO dto);

    DTO vo2dto(VO vo);

    DO dto2do(DTO dto);

    List<DTO> do2dto4List(List<DO> doObj);

    List<VO> dto2vo4List(List<DTO> dto);

    IPage<VO> dto2vo4Page(IPage<DTO> dto);

    IPage<DTO> do2dto4Page(IPage<DO> dto);

    List<DTO> vo2dto4List(List<VO> vo);

    List<DO> dto2do4List(List<DTO> dto);


}
