package com.agri.platform.entity.planning;


import lombok.Data;
import java.time.LocalDateTime;

// WarningHandleRecord.java
@Data
public class WarningHandleRecord {
    private Long id; // 记录ID - 修改这里
    private Long warningId; // 关联预警ID
    private Long handlePerson; // 处理人（农场主ID）- 修改这里
    private String handleMeasure; // 处理措施
    private LocalDateTime handleTime; // 处理时间
    private String effectFeedback; // 效果反馈
}