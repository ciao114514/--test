package com.example.hospital.service;


import com.example.hospital.pojo.Doctor;
import com.example.hospital.pojo.Subject;

import java.util.HashMap;
import java.util.List;

public interface SubjectService {


    List<Subject> selectSubjects();


    /**
     * 增加类型
     */
    Boolean addSubject(Subject subject);
    /**
     * 删除类型
     */
    Boolean deleteSubject(int kid);

    Subject selectById(int kid);
    /**
     * 修改信息
     */
    Boolean modifySubject(Subject subject);
    /**
     * 查询类型
     * @param pageNumber
     * @param size
     * @param query
     * @return
     */

    HashMap<String, Object> findAllSubjects(int pageNumber, int size, String query);

}
