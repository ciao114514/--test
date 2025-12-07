package com.example.hospital.service;

import com.example.hospital.pojo.Patient;

import java.util.HashMap;
import java.util.List;

public interface PatientService {
    /**
     * 登录数据校验
     * */
    Patient login(int pId, String pPassword);
    /**
     * 分页模糊查询所有学生信息
     */
    HashMap<String, Object> findAllPatients(int pageNumber, int size, String query);
    /**
     * 删除学生信息
     */
    Boolean deletePatient(int pId);
    /**
     * 根据学生id查询学生信息
     */
    Patient findPatientById(int pId);
    /**
     * 增加学生信息
     */
    Boolean addPatient(Patient patient);
    /**
     * 统计学生男女人数
     */
    List<Integer> patientAge();

   Boolean insertPatient(Patient patient);
}
