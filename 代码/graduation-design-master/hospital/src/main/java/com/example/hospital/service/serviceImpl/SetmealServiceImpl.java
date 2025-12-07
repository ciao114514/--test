package com.example.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hospital.mapper.SetmealMapper;
import com.example.hospital.pojo.Setmeal;
import com.example.hospital.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("SetmealService")
public class SetmealServiceImpl implements SetmealService {


    @Resource
    private SetmealMapper mapper;


    @Override
    public Boolean addSetmeal(Setmeal setmeal) {
        this.mapper.insert(setmeal);
        return true;
    }

    @Override
    public Boolean deleteSetmeal(int tid) {
        this.mapper.deleteById(tid);
        return true;
    }

    @Override
    public Setmeal selectById(int tid) {

        return  this.mapper.selectById(tid);
    }

    @Override
    public Boolean modifySetmeal(Setmeal setmeal) {
        int i = this.mapper.updateById(setmeal);
        return true;
    }

    @Override
    public HashMap<String, Object> findAllSetmeals(int pageNumber, int size, String query) {
        Page<Setmeal> page = new Page<>(pageNumber, size);
        QueryWrapper<Setmeal> wrapper = new QueryWrapper<>();

        wrapper.orderByAsc("sert");
        IPage<Setmeal> iPage = this.mapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("setmeals", iPage.getRecords()); //查询到的记录
        return hashMap;
    }

    @Override
    public HashMap<String, Object> findAllSetmealss(int pageNumber, int size, String query,int tid) {
        Page<Setmeal> page = new Page<>(pageNumber, size);
        QueryWrapper<Setmeal> wrapper = new QueryWrapper<>();

        wrapper.eq("tid", tid).orderByAsc("sert");
        IPage<Setmeal> iPage = this.mapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("setmeals", iPage.getRecords()); //查询到的记录
        return hashMap;
    }
}
