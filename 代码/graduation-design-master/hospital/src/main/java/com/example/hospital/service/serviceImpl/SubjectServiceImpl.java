package com.example.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hospital.mapper.SubjectMapper;
import com.example.hospital.pojo.Doctor;
import com.example.hospital.pojo.Subject;
import com.example.hospital.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("SubjectService")
public class SubjectServiceImpl implements SubjectService {

    @Resource
     SubjectMapper mapper;

    @Override
    public List<Subject> selectSubjects() {
        return mapper.selectSubjects();
    }



    @Override
    public Boolean addSubject(Subject subject) {
        this.mapper.insert(subject);
        return true;
    }

    @Override
    public Boolean deleteSubject(int kid) {
        this.mapper.deleteById(kid);
        return true;
    }
    /**
     * 根据id查找类型
     */
    @Override
    public Subject selectById(int kid) {
        return mapper.selectById(kid);
    }

    @Override
    public Boolean modifySubject(Subject subject) {
        int i = this.mapper.updateById(subject);
        return true;
    }


    @Override
    public HashMap<String, Object> findAllSubjects(int pageNumber, int size, String query) {
        Page<Subject> page = new Page<>(pageNumber, size);
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        //wrapper.like("d_name", query).orderByDesc("d_state");
        //wrapper.orderByDesc("sert");
        wrapper.orderByAsc("sert");
        IPage<Subject> iPage = this.mapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("subjects", iPage.getRecords()); //查询到的记录
        return hashMap;
    }
}
