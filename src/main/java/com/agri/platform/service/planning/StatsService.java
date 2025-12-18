package com.agri.platform.service.planning;

import com.agri.platform.DTO.planning.StatsDTO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class StatsService {

    @Resource
    private DataSource dataSource;

    // 查询统计数据，封装成 StatsDTO 返回给前端
    public StatsDTO getStats() {
        StatsDTO stats = new StatsDTO();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // 示例：查询合作农户数（实际根据你的业务逻辑修改SQL）
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM contact");
            if (rs1.next()) {
                stats.setFarmerCount(rs1.getInt(1));  // 假设contact表存农户信息，实际按你的表结构改
            }

            // 其他统计项：覆盖农场数、合作省份数、产量提升（同理查询后设置）
            stats.setFarmCount(850);  // 测试用默认值，后续替换为真实SQL查询
            stats.setProvinceCount(42);
            stats.setYieldIncrease(35);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
}