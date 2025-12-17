package com.agri.platform.service.planning.impl;

import com.agri.platform.entity.planning.Land;
import com.agri.platform.mapper.planning.LandMapper;
import com.agri.platform.service.planning.LandService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

@Service
public class LandServiceImpl implements LandService {

    @Resource
    private LandMapper landMapper;

    @Override
    public Land getById(Long landId) {
        return landMapper.selectById(landId);
    }

    @Override
    public boolean save(Land land) {
        return landMapper.insert(land) > 0;
    }

    @Override
    public boolean update(Land land) {
        return landMapper.update(land) > 0;
    }

    @Override
    public boolean removeById(Long landId) {
        return landMapper.deleteById(landId) > 0;
    }

    @Override
    public List<Land> getByFarmerId(Long farmerId) {
        return landMapper.selectByFarmerId(farmerId);
    }

    @Override
    public Land getLandById(Long landId) {
        return landMapper.selectLandById(landId);
    }
}