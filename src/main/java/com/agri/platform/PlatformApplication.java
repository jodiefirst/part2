//package com.agri.platform;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class PlatformApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(PlatformApplication.class, args);
//	}
//
//}
package com.agri.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.agri.platform.mapper")
@EnableScheduling // 启用定时任务
public class PlatformApplication {  // 保持原类名
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
        System.out.println("项目启动成功！访问地址：http://localhost:8080");
    }
}
