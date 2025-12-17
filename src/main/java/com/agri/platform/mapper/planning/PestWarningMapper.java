package com.agri.platform.mapper.planning;

import com.agri.platform.entity.planning.PestWarning;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PestWarningMapper {
    // 自定义方法：按处理状态查询预警
    List<PestWarning> selectByHandleStatus(Integer handleStatus);
    PestWarning selectById(Long warningId);
    int insert(PestWarning warning);
    int update(PestWarning warning);
    int deleteById(Long warningId);
    int updateStatusById(PestWarning warning);

    // 自定义方法：按预警等级查询预警
    List<PestWarning> selectByWarningLevel(Integer warningLevel);
    
    // 添加查询所有预警记录的方法
    List<PestWarning> selectAll();
}