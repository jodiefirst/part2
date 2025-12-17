package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.Land;
import java.util.List;

public interface LandService {
    // 标准CRUD方法
    Land getById(Long landId);
    boolean save(Land land);
    boolean update(Land land);
    boolean removeById(Long landId);
    
    List<Land> getByFarmerId(Long farmerId);
    Land getLandById(Long landId);
}