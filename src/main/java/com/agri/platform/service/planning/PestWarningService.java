package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.entity.planning.PlantingPlan;
import com.agri.platform.entity.planning.SensorData;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.mapper.planning.PlantingPlanMapper;
import com.agri.platform.mapper.planning.SensorDataMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PestWarningService {
    @Autowired
    private SensorDataMapper sensorDataMapper;
    @Autowired
    private PestWarningMapper pestWarningMapper;
    @Autowired
    private PlantingPlanMapper plantingPlanMapper; // 新增：注入PlantingPlanMapper

    // 定时任务：每小时分析一次所有土地的环境数据（cron表达式：0 0 * * * ?）
    @Scheduled(cron = "0 0 * * * ?")
    public void autoPredictPestRisk() {
        // 模拟所有土地ID（实际应从种植计划中查询）
        List<Long> landIds = new ArrayList<>();
        landIds.add(1L); // 土地1
        landIds.add(2L); // 土地2

        // 遍历每块土地，分析风险
        for (Long landId : landIds) {
            // 获取该土地的所有最新传感器数据
            List<SensorData> allLatestData = sensorDataMapper.queryByLandAndRange(landId, LocalDateTime.now().minusHours(1), LocalDateTime.now());

            // 将数据按传感器类型分组
            Map<String, SensorData> latestDataMap = new HashMap<>();
            for (SensorData data : allLatestData) {
                String sensorType = data.getSensorType();
                if (!latestDataMap.containsKey(sensorType) || data.getRecordedAt().isAfter(latestDataMap.get(sensorType).getRecordedAt())) {
                    latestDataMap.put(sensorType, data);
                }
            }

            // 核心：风险判断逻辑（高温高湿→红色预警）
            PestWarning warning = judgeRisk(landId, latestDataMap);
            if (warning != null) {
                // 查询土地对应的作物类型
                PlantingPlan plantingPlan = plantingPlanMapper.selectByLandId(landId);
                if (plantingPlan != null) {
                    warning.setCropType(plantingPlan.getCropType());
                } else {
                    warning.setCropType("未知"); // 默认值
                }
                
                pestWarningMapper.insert(warning);
                // 红色预警（等级3）触发报警
                if (warning.getWarningLevel() == 3) {
                    triggerRedAlarm(warning);
                }
            }
        }
    }

    // 风险判断逻辑（可自定义扩展）
    private PestWarning judgeRisk(Long landId, Map<String, SensorData> dataMap) {
        PestWarning warning = new PestWarning();
        warning.setLandId(landId);
        warning.setHandleStatus(0); // 0=未处理

        // 获取各种传感器数据
        SensorData soilTempData = dataMap.get("soil_temperature");
        SensorData soilHumData = dataMap.get("soil_humidity");
        SensorData airTempData = dataMap.get("air_temperature");
        SensorData airHumData = dataMap.get("air_humidity");

        // 如果缺少必要数据，跳过风险判断
        if (soilTempData == null || soilHumData == null || airTempData == null || airHumData == null) {
            return null;
        }

        // 条件1：土壤温度>30℃ + 土壤湿度>80% → 小麦锈病（红色预警）
        boolean wheatRustRisk = soilTempData.getValue().compareTo(new java.math.BigDecimal("30")) > 0
                && soilHumData.getValue().compareTo(new java.math.BigDecimal("80")) > 0;

        // 条件2：空气温度>28℃ + 空气湿度>75% → 蚜虫（黄色预警）
        boolean aphidRisk = airTempData.getValue().compareTo(new java.math.BigDecimal("28")) > 0
                && airHumData.getValue().compareTo(new java.math.BigDecimal("75")) > 0;

        if (wheatRustRisk) {
            warning.setWarningType("小麦锈病");
            warning.setWarningLevel(3); // 红色预警
            warning.setWarningReason("土壤温度：" + soilTempData.getValue() + "℃，土壤湿度：" + soilHumData.getValue() + "%，符合高风险条件");
            warning.setCreateTime(LocalDateTime.now());
            warning.setUpdateTime(LocalDateTime.now());
            return warning;
        } else if (aphidRisk) {
            warning.setWarningType("蚜虫");
            warning.setWarningLevel(2); // 黄色预警
            warning.setWarningReason("空气温度：" + airTempData.getValue() + "℃，空气湿度：" + airHumData.getValue() + "%，符合中风险条件");
            warning.setCreateTime(LocalDateTime.now());
            warning.setUpdateTime(LocalDateTime.now());
            return warning;
        }

        return null; // 无风险
    }

    // 红色预警触发（实际可扩展为短信、APP推送）
    private void triggerRedAlarm(PestWarning warning) {
        System.out.println("【红色报警】土地ID：" + warning.getLandId() + "，作物类型：" + warning.getCropType() + "，预警类型：" + warning.getWarningType() + "，原因：" + warning.getWarningReason() + "，请立即处理！");
    }
}