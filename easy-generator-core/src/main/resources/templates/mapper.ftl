package ${targetPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${modulePackage}.domain.dto.${domainObjectClassName}Request;
import ${modulePackage}.domain.dto.${domainObjectClassName}Response;
import ${modulePackage}.domain.entity.${domainObjectClassName};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ${domainObjectClassName}Mapper extends BaseMapper<${domainObjectClassName}> {

    int insertBatch(@Param("list") List<${domainObjectClassName}> list);

    Page<UserResponse> selectPage(Page<UserResponse> page, UserRequest userRequestVO);

}
