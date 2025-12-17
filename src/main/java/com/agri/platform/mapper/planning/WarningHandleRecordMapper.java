package com.agri.platform.mapper.planning;

import com.agri.platform.entity.planning.WarningHandleRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WarningHandleRecordMapper {
    // 自定义方法：按预警ID查询处理记录
    List<WarningHandleRecord> selectByWarningId(Long warningId);
    WarningHandleRecord selectById(Long recordId);
    int insert(WarningHandleRecord record);
    int update(WarningHandleRecord record);
    int deleteById(Long recordId);
    
    // 添加获取所有处理记录的方法
    List<WarningHandleRecord> selectAll();
}