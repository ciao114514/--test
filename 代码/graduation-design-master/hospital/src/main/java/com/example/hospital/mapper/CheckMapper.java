package com.example.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.pojo.Checks;

import java.util.List;

public interface CheckMapper extends BaseMapper<Checks> {
    List<Checks>  selectmyChecks(String kname,Integer tid);

    List<Checks>  selectChecksGetKname();

    List<Checks> selectChecksByTid(Integer tid);


    List<Checks> selectBykname(String kname);

}
