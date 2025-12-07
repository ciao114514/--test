package com.example.hospital.service;


import com.example.hospital.pojo.Setmeal;

import java.util.HashMap;

public interface SetmealService {


    /**
     * 增加类型
     */
    Boolean addSetmeal(Setmeal setmeal);
    /**
     * 删除类型
     */
    Boolean deleteSetmeal(int tid);

    Setmeal selectById(int tid);
    /**
     * 修改信息
     */
    Boolean modifySetmeal(Setmeal setmeal);
    /**
     * 查询类型
     * @param pageNumber
     * @param size
     * @param query
     * @return
     */

    HashMap<String, Object> findAllSetmeals(int pageNumber, int size, String query);
    HashMap<String, Object> findAllSetmealss(int pageNumber, int size, String query,int tid) ;

}
