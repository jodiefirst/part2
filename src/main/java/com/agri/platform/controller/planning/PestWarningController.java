package com.agri.platform.controller.planning;

import com.agri.platform.common.Result;
import com.agri.platform.DTO.planning.WarningHandleRecordDTO;
import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.entity.planning.WarningHandleRecord;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.service.planning.WarningHandleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pest-warning") // 修改为/pest-warning
public class PestWarningController {
    @Autowired
    private PestWarningMapper pestWarningMapper;
    @Autowired
    private WarningHandleRecordService handleService;

    // 按处理状态查询预警（0=未处理，1=处理中，2=已处理）
    // 访问：GET http://localhost:8080/pest-warning/status/0
    @GetMapping("/status/{status}")
    public List<PestWarning> getByStatus(@PathVariable Integer status) {
        return pestWarningMapper.selectByHandleStatus(status);
    }

    // 按预警等级查询（3=红色预警）
    // 访问：GET http://localhost:8080/pest-warning/level/3
    @GetMapping("/level/{level}")
    public List<PestWarning> getByLevel(@PathVariable Integer level) {
        return pestWarningMapper.selectByWarningLevel(level);
    }

    // 修改为查询所有预警记录
    @GetMapping("/list")
    public Result<List<PestWarning>> getPestWarnings() {
        try {
            // 查询所有预警记录
            List<PestWarning> warnings = pestWarningMapper.selectAll();
            return Result.success(warnings); // 返回成功结果
        } catch (Exception e) {
            // 打印错误日志，方便排查
            e.printStackTrace();
            return Result.error("获取预警记录失败：" + e.getMessage());
        }
    }

    @PostMapping("/add") // 接口地址：/warning-handle/add
    public Result<String> addHandleRecord(@RequestBody WarningHandleRecord record) {
        try {
            // 补充默认值（前端没传的字段）
            record.setHandlePerson(1L); // 临时用1（后续登录后改从登录信息获取）
            record.setHandleTime(LocalDateTime.now()); // 处理时间（如果前端没有提供的话）
            // 调用Service保存
            boolean success = handleService.saveHandleRecord(record);
            if (success) {
                return Result.success("处理成功！");
            } else {
                return Result.error("处理失败，请重试");
            }
        } catch (Exception e) {
            return Result.error("服务器错误，处理失败");
        }
    }
}