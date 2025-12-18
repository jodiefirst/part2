package com.agri.platform.service.planning.impl;

import com.agri.platform.entity.planning.Farmer;
import com.agri.platform.mapper.planning.FarmerMapper;
import com.agri.platform.service.planning.FarmerService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource; // 修改这行，使用jakarta包

@Service
public class FarmerServiceImpl implements FarmerService {

    @Resource
    private FarmerMapper farmerMapper;

    @Override
    public Farmer getByFarmerId(Long farmerId) {
        return farmerMapper.selectByFarmerId(farmerId);
    }

    @Override
    public Farmer getById(Long id) {
        return farmerMapper.selectById(id);
    }

    @Override
    public boolean save(Farmer farmer) {
        return farmerMapper.insert(farmer) > 0;
    }

    @Override
    public boolean update(Farmer farmer) {
        return farmerMapper.update(farmer) > 0;
    }

    @Override
    public boolean removeById(Long id) {
        return farmerMapper.deleteById(id) > 0;
    }
}