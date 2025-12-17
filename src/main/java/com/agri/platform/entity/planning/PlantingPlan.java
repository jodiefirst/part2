package com.agri.platform.entity.planning;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Lombok注解，自动生成getter/setter
public class PlantingPlan {
    private Long planId; // 计划ID（对应表中plan_id）
    private Long farmerId; // 农场主ID（关联用户表id）
    private Long landId; // 地块ID（关联地块表land_id）
    private String cropType; // 作物类型
    private BigDecimal plantingArea; // 种植面积（亩）
    private LocalDate plantingTime; // 种植时间
    private BigDecimal expectedYield; // 预估产量（公斤）
    private String yieldBasis; // 产量预估依据
    private LocalDateTime createTime; // 创建时间
}