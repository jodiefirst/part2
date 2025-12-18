//package com.agri.platform.service;
//
//import com.agri.platform.entity.SensorData;
//import com.agri.platform.mapper.SensorDataMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class SensorDataService {
//    @Autowired
//    private SensorDataMapper sensorDataMapper;
//
//    // 获取某块土地的实时数据（最新一条）
//    public SensorData getRealTimeData(Long landId) {
//        return sensorDataMapper.selectLatestByLandId(landId);
//    }
//
//    // 获取历史趋势数据（按时间范围）
//    public List<SensorData> getHistoryData(Long landId, Date startTime, Date endTime) {
//        return sensorDataMapper.selectByLandIdAndTimeRange(landId, startTime, endTime);
//    }
//
//    // 模拟传感器数据上报（实际项目中由硬件推送，这里用于测试）
//    public boolean reportSensorData(SensorData data) {
//        return sensorDataMapper.insert(data) > 0;
//    }
//}
package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.SensorData;
import com.agri.platform.mapper.planning.SensorDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SensorDataService {
    @Autowired
    private SensorDataMapper sensorDataMapper;

    // 随机数生成器
    private final Random random = new Random();

    // 传感器类型列表
    private static final List<String> SENSOR_TYPES = List.of(
            "soil_humidity",
            "soil_temperature",
            "air_temperature",
            "air_humidity"
    );

    // 获取某块土地的实时数据（最新一条）
    public SensorData getRealTimeData(Long landId) {
        return sensorDataMapper.selectLatestByLandId(landId);
    }

    // 获取历史趋势数据（按时间范围）
    public List<SensorData> getHistoryData(Long landId, LocalDateTime startTime, LocalDateTime endTime) {
        return sensorDataMapper.queryByLandAndRange(landId, startTime, endTime);
    }

    // 模拟传感器数据上报（实际项目中由硬件推送，这里用于测试）
    public boolean reportSensorData(SensorData data) {
        return sensorDataMapper.insert(data) > 0;
    }

    // 生成随机传感器数据
    public SensorData generateRandomSensorData(Long landId) {
        // 随机选择一个传感器类型
        String sensorType = SENSOR_TYPES.get(random.nextInt(SENSOR_TYPES.size()));

        SensorData data = new SensorData();
        data.setLandId(landId);
        data.setSensorType(sensorType);

        // 根据传感器类型生成合理范围内的随机数据
        switch (sensorType) {
            case "soil_humidity":
                // 土壤湿度：40%-90%
                data.setValue(generateRandomBigDecimal(40, 90, 2));
                break;
            case "soil_temperature":
                // 土壤温度：15℃-35℃
                data.setValue(generateRandomBigDecimal(15, 35, 1));
                break;
            case "air_temperature":
                // 空气温度：10℃-40℃
                data.setValue(generateRandomBigDecimal(10, 40, 1));
                break;
            case "air_humidity":
                // 空气湿度：30%-95%
                data.setValue(generateRandomBigDecimal(30, 95, 2));
                break;
        }

        data.setRecordedAt(LocalDateTime.now());
        return data;
    }

    // 辅助方法：生成指定范围内的随机BigDecimal
    private BigDecimal generateRandomBigDecimal(double min, double max, int scale) {
        double randomValue = min + (max - min) * random.nextDouble();
        return BigDecimal.valueOf(randomValue).setScale(scale, RoundingMode.HALF_UP);
    }

    // 定时任务：每5分钟为所有土地生成一次传感器数据
    @Scheduled(cron = "0 */5 * * * ?")
    public void autoGenerateSensorData() {
        // 模拟的土地ID列表
        List<Long> landIds = List.of(1L, 2L);

        // 为每块土地生成所有类型的传感器数据
        for (Long landId : landIds) {
            for (String sensorType : SENSOR_TYPES) {
                SensorData data = new SensorData();
                data.setLandId(landId);
                data.setSensorType(sensorType);

                // 根据传感器类型生成合理范围内的随机数据
                switch (sensorType) {
                    case "soil_humidity":
                        data.setValue(generateRandomBigDecimal(40, 90, 2));
                        break;
                    case "soil_temperature":
                        data.setValue(generateRandomBigDecimal(15, 35, 1));
                        break;
                    case "air_temperature":
                        data.setValue(generateRandomBigDecimal(10, 40, 1));
                        break;
                    case "air_humidity":
                        data.setValue(generateRandomBigDecimal(30, 95, 2));
                        break;
                }

                data.setRecordedAt(LocalDateTime.now());
                sensorDataMapper.insert(data);
            }
        }

        System.out.println("自动生成传感器数据完成：" + LocalDateTime.now());
    }
}