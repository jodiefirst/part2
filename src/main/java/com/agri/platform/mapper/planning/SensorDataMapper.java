//package com.agri.platform.mapper;
//
//import com.agri.platform.entity.SensorData;
//import org.apache.ibatis.annotations.Mapper;
//import java.util.Date;
//import java.util.List;
//
//@Mapper
//public interface SensorDataMapper {
//    // 自定义方法：查询某块土地的最新一条传感器数据
//    SensorData selectLatestByLandId(Long landId);
//    SensorData selectById(Long dataId);
//    int insert(SensorData data);
//    int update(SensorData data);
//    int deleteById(Long dataId);
//
//    // 自定义方法：按土地ID和时间范围查询历史数据
//    List<SensorData> selectByLandIdAndTimeRange(Long landId, Date startTime, Date endTime);
//}

package com.agri.platform.mapper.planning;

import com.agri.platform.entity.planning.SensorData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SensorDataMapper {
    // 新的insert方法
    @Insert("INSERT INTO sensor_data(land_id,sensor_type,value,recorded_at) VALUES(#{landId},#{sensorType},#{value},#{recordedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SensorData data);

    // 新的查询方法
    List<SensorData> queryByLandAndRange(@Param("landId") Long landId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    // 保留原来的方法以确保兼容性
    SensorData selectLatestByLandId(Long landId);
    SensorData selectById(Long id);
    int update(SensorData data);
    int deleteById(Long id);
}