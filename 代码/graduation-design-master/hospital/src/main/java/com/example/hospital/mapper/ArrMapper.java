package com.example.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.pojo.Arr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArrMapper extends BaseMapper<Arr> {

    /**
     * 根据日期查询排班信息
     */
    List<Arr> findByTime(@Param("ar_time") String arTime, @Param("d_section") String dSection);

}
