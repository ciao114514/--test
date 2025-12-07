package com.example.hospital.service;

import com.example.hospital.pojo.Setmeal;
import com.example.hospital.pojo.SetmealChecks;

import java.util.List;

public interface SetmealChecksService {
    /**
     * 增加
     */
    Boolean addSetmealChecks(SetmealChecks setmealChecks);

    Boolean deleteSetmealChecks(int tid);

    List<Setmeal> selectSetmealChecks();
}
