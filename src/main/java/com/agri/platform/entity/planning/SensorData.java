package com.agri.platform.entity.planning;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
//public class SensorData {
//    private Long dataId; // 数据ID
//    private Long landId; // 土地ID
//    private BigDecimal soilHumidity; // 土壤湿度（%）
//    private BigDecimal soilTemperature; // 土壤温度（℃）
//    private BigDecimal airTemperature; // 空气温度（℃）
//    private BigDecimal airHumidity; // 空气湿度（%）
//    private LocalDateTime collectTime; // 采集时间
//}
public class SensorData {
    private Long id; // 数据ID（新主键名称）
    private Long landId; // 土地ID
    private String sensorType; // 传感器类型（如soil_humidity, soil_temperature等）
    private BigDecimal value; // 传感器数值
    private LocalDateTime recordedAt; // 记录时间（新字段名称）
}