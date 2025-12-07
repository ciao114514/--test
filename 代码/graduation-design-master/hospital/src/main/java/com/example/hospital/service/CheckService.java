package com.example.hospital.service;

import com.example.hospital.pojo.Checks;

import java.util.HashMap;
import java.util.List;

public interface CheckService {
    /**
     * 分页模糊查询所有检查信息
     */
    HashMap<String, Object> findAllChecks(int pageNumber, int size, String query,String chid);
    /**
     * 根据id查找药物
     */
    Checks findCheck(int chId);

    List<Checks> selectsssChecks(Integer tid);



    /**
     * 增加检查信息
     */
    Boolean addCheck(Checks checks);
    /**
     * 删除检查信息
     */
    Boolean deleteCheck(int chId);
    /**
     * 修改检查信息
     */
    Boolean modifyCheck(Checks checks);


    List<Checks> selectBykname(String kname);
}
