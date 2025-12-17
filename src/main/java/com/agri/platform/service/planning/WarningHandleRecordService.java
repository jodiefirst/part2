package com.agri.platform.service.planning;

import com.agri.platform.DTO.planning.WarningHandleRecordDTO;
import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.entity.planning.WarningHandleRecord;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.mapper.planning.WarningHandleRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarningHandleRecordService {
    @Autowired
    private WarningHandleRecordMapper handleRecordMapper;
    @Autowired
    private PestWarningMapper warningMapper;

    // 查询所有处理记录，包含关联的预警信息
    public List<WarningHandleRecordDTO> listAllRecords() {
        try {
            // 调用Mapper查询所有处理记录
            List<WarningHandleRecord> records = handleRecordMapper.selectAll();
            if (records == null || records.isEmpty()) {
                return new ArrayList<>();
            }
            
            // 转换为包含预警信息的DTO列表
            List<WarningHandleRecordDTO> result = new ArrayList<>();
            for (WarningHandleRecord record : records) {
                WarningHandleRecordDTO dto = new WarningHandleRecordDTO();
                
                // 复制处理记录的基本信息
                BeanUtils.copyProperties(record, dto);
                
                // 查询关联的预警信息
                PestWarning warning = warningMapper.selectById(record.getWarningId());
                if (warning != null) {
                    // 设置关联的预警信息
                    dto.setWarningType(warning.getWarningType() != null ? warning.getWarningType() : "未知");
                    
                    // 根据预警类型推断作物类型
                    if (warning.getWarningType() != null) {
                        if (warning.getWarningType().contains("小麦")) {
                            dto.setCropType("小麦");
                        } else if (warning.getWarningType().contains("水稻")) {
                            dto.setCropType("水稻");
                        } else if (warning.getWarningType().contains("玉米")) {
                            dto.setCropType("玉米");
                        } else if (warning.getWarningType().contains("大豆")) {
                            dto.setCropType("大豆");
                        } else {
                            dto.setCropType("未知");
                        }
                    } else {
                        dto.setCropType("未知");
                    }
                    
                    dto.setWarningReason(warning.getWarningReason());
                    dto.setWarningLevel(warning.getWarningLevel());
                } else {
                    // 预警信息不存在时，设置默认值
                    dto.setWarningType("未知");
                    dto.setCropType("未知");
                }
                
                result.add(dto);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace(); // 打印错误（方便排查）
            return new ArrayList<>(); // 出错返回空列表
        }
    }

    // 查询按预警ID查询处理记录
    public List<WarningHandleRecord> listRecordsByWarningId(Long warningId) {
        return handleRecordMapper.selectByWarningId(warningId);
    }

    // 确保事务正确回滚
    // 修改 WarningHandleRecordService.java 中的 addHandleRecord 方法
    @Transactional
    public boolean addHandleRecord(WarningHandleRecord record) {
        try {
            // 参数验证
            if (record == null || record.getWarningId() == null) {
                throw new IllegalArgumentException("预警ID不能为空");
            }
            
            // 保存处理记录
            int recordRows = handleRecordMapper.insert(record);
            if (recordRows <= 0) {
                throw new Exception("保存处理记录失败");
            }
        
            // 更新对应预警的状态
            PestWarning warning = new PestWarning();
            warning.setWarningId(record.getWarningId());
            warning.setHandleStatus(2); // 2=已处理
            warning.setStatus("已处理");
            warning.setUpdateTime(LocalDateTime.now());
            int updateRows = warningMapper.update(warning);
            
            if (updateRows <= 0) {
                throw new Exception("未找到对应预警，更新状态失败");
            }
        
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("参数错误: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("处理记录添加失败: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean saveHandleRecord(WarningHandleRecord record) {
        try {
            // 参数验证
            if (record == null) {
                throw new IllegalArgumentException("处理记录不能为空");
            }
            if (record.getWarningId() == null) {
                throw new IllegalArgumentException("预警ID不能为空");
            }
            
            // 设置默认值
            if (record.getHandlePerson() == null) {
                record.setHandlePerson(1L); // 默认农场主ID
            }
            if (record.getHandleTime() == null) {
                record.setHandleTime(LocalDateTime.now()); // 默认当前时间
            }
    
            // 保存处理记录
            int insertRows = handleRecordMapper.insert(record);
            if (insertRows <= 0) {
                throw new Exception("保存处理记录失败");
            }
    
            // 更新对应的预警状态
            PestWarning warning = new PestWarning();
            warning.setWarningId(record.getWarningId());
            warning.setStatus("已处理");
            warning.setHandleStatus(2); // 2=已处理
            warning.setUpdateTime(LocalDateTime.now());
            
            int updateRows = warningMapper.update(warning);
            if (updateRows <= 0) {
                throw new Exception("未找到对应预警，更新状态失败");
            }
    
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("参数错误: " + e.getMessage());
            throw e; // 重新抛出参数错误
        } catch (Exception e) {
            System.err.println("保存处理记录失败: " + e.getMessage());
            return false;
        }
    }
}