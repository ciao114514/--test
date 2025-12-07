package com.example.hospital.service.serviceImpl;


import com.example.hospital.mapper.CheckMapper;
import com.example.hospital.mapper.SetmealChecksMapper;
import com.example.hospital.mapper.SetmealMapper;
import com.example.hospital.pojo.Checks;
import com.example.hospital.pojo.Setmeal;
import com.example.hospital.pojo.SetmealChecks;
import com.example.hospital.service.SetmealChecksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SetmealChecksService")
public class SetmealChecksServiceImpl implements SetmealChecksService {

    @Resource
    SetmealChecksMapper mapper;
    @Resource
    SetmealMapper setmealMapper;
    @Resource
    CheckMapper checkMapper;

    @Override
    public Boolean addSetmealChecks(SetmealChecks setmealChecks) {
        this.mapper.insert(setmealChecks);
        return true;
    }

    @Override
    public Boolean deleteSetmealChecks(int tid) {
        this.mapper.deleteById(tid);
        return true;
    }

    @Override
    public List<Setmeal> selectSetmealChecks() {
        List<Setmeal> s =setmealMapper.selectSetmeals();
        for (Setmeal ss:s) {

            List<Checks> c =checkMapper.selectChecksByTid(ss.getTid());
            ss.setChecks(c);
        }
        return s;
    }
}
