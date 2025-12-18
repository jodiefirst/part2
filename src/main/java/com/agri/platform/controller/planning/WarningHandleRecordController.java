package com.agri.platform.controller.planning;

import com.agri.platform.common.Result;
import com.agri.platform.DTO.planning.WarningHandleRecordDTO;
import com.agri.platform.entity.planning.WarningHandleRecord;
import com.agri.platform.service.planning.WarningHandleRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warning-handle")
public class WarningHandleRecordController {
    @Autowired
    private WarningHandleRecordService recordService;

    // 新增处理记录
    @PostMapping("/add")
    public Map<String, Object> addRecord(@RequestBody WarningHandleRecordDTO recordDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 转换为实体类
            WarningHandleRecord record = new WarningHandleRecord();
            BeanUtils.copyProperties(recordDTO, record);
            
            // 补充默认值
            if (record.getHandlePerson() == null) {
                record.setHandlePerson(1L);
            }
            if (record.getHandleTime() == null) {
                record.setHandleTime(LocalDateTime.now());
            }
            
            boolean success = recordService.addHandleRecord(record);
            if (success) {
                result.put("code", 200);
                result.put("message", "处理记录提交成功！预警状态已更新为\"已处理\"");
            } else {
                result.put("code", 500);
                result.put("message", "提交失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "处理失败：" + e.getMessage());
        }
        return result;
    }

    // 按预警ID查询处理记录
    @GetMapping("/warning/{warningId}")
    public Map<String, Object> getByWarningId(@PathVariable Long warningId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<WarningHandleRecord> records = recordService.listRecordsByWarningId(warningId);
            result.put("code", 200);
            result.put("data", records);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }

    // 修改获取所有处理记录的接口
    @GetMapping("/list")
    public Map<String, Object> getAllRecords() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<WarningHandleRecordDTO> records = recordService.listAllRecords();
            result.put("code", 200);
            result.put("data", records);
            result.put("total", records.size());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "查询失败：" + e.getMessage());
            result.put("data", new ArrayList<>());
        }
        return result;
    }
}