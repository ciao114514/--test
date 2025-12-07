package com.example.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.pojo.Setmeal;

import java.util.List;

public interface SetmealMapper extends BaseMapper<Setmeal> {

    List<Setmeal> selectSetmeals();
}
