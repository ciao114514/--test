package com.example.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.pojo.Subject;
import java.util.List;

public interface SubjectMapper extends BaseMapper<Subject> {


    List<Subject> selectSubjects();

    Subject  selectByKname(String kname);

}
