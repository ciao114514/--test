package com.example.hospital.service;

import com.example.hospital.pojo.Arr;

import java.util.List;

public interface ArrService {
    /**
     * 根据日期查询排班信息
     */
    List<Arr> findByTime(String arTime, String dSection);
    /**
     * 增加排班信息
     */
    Boolean addArr(Arr arrange);
    /**
     * 删除排班信息
     */
    Boolean deleteArr(String arId);
}
