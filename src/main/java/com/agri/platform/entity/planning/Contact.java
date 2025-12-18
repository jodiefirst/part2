package com.agri.platform.entity.planning;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Contact {
    private Long id;  // 主键，bigint类型（MySQL中自动对应）
    private String name;  // 姓名，varchar类型（默认长度255）
    private String phone;  // 电话，varchar类型
    private String email;  // 邮箱，varchar类型
    private String subject;  // 咨询主题，varchar类型
    private String message;  // 留言内容，text类型（MyBatis-Plus自动识别长文本）

    private LocalDateTime createTime;  // 提交时间，datetime类型
}