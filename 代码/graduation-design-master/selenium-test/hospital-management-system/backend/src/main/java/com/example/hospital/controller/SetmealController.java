package com.example.hospital.controller;

import com.alibaba.fastjson.JSON;
import com.example.hospital.pojo.Setmeal;
import com.example.hospital.pojo.SetmealChecks;
import com.example.hospital.service.SetmealChecksService;
import com.example.hospital.service.SetmealService;
import com.example.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套餐
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService service;
    @Autowired
    private SetmealChecksService setmealChecksService;



    /**
     * 根据id查找套餐
     */
    @RequestMapping("getSetmealCheck")
    public ResponseData getSetmealCheck() {

        return ResponseData.success("查询套餐成功", this.setmealChecksService.selectSetmealChecks());
    }


    /**
     * 添加套餐和项目
     * @param tid,setmealchecks
     * @return
     */

    @RequestMapping("addSetmealChecks")
    @ResponseBody
    public ResponseData addSetmealChecks(Integer tid,String params){

        setmealChecksService.deleteSetmealChecks(tid);

       List<String> ls = JSON.parseArray(params,String.class);
        System.out.println(ls);
       // for (String sc: setmealchecks ) {
        for (String sc: ls ) {
            SetmealChecks setmealChecks = new SetmealChecks();
            setmealChecks.setChId(Integer.valueOf(sc));
            setmealChecks.setTid(tid);
            Boolean bo = setmealChecksService.addSetmealChecks(setmealChecks);
        }
            return ResponseData.success("增加套餐项目信息成功");
    }
    /**
     * 添加套餐
     * @param setmeal
     * @return
     */

    @RequestMapping("addSetmeal")
    public ResponseData addSetmeal(Setmeal setmeal){
        Boolean bo = service.addSetmeal(setmeal);

        if (bo) {
            return ResponseData.success("增加套餐信息成功");
        }
        return ResponseData.fail("增加套餐信息失败！账号或已被占用");
    }

    /**
     * 删除套餐信息
     */
    @RequestMapping("deleteSetmeal")
    public ResponseData deleteSetmeal(@RequestParam(value = "tid") int tid) {
        Boolean bo = this.service.deleteSetmeal(tid);
        if (bo){
            return ResponseData.success("删除套餐信息成功");
        }
        return ResponseData.fail("删除套餐信息失败");
    }
    /**
     * 根据id查找套餐
     */
    @RequestMapping("findSetmeal")
    public ResponseData findSetmeal(@RequestParam(value = "tid") int tid) {
        return ResponseData.success("查询套餐成功", this.service.selectById(tid));
    }
    /**
     * 修改套餐信息
     * bug: dState会自动更新为0
     */
    @RequestMapping("modifySetmeal")
    @ResponseBody
    public ResponseData modifySetmeal(Setmeal setmeal) {
        this.service.modifySetmeal(setmeal);
        return ResponseData.success("修改套餐信息成功");
    }
    /**
     * 分页模糊查询类型信息
     */
    @RequestMapping("findAllSetmeals")
    public ResponseData findAllSetmeals(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("返回套餐信息成功",  this.service.findAllSetmeals(pageNumber, size, query));
    }
    /**
     * 分页模糊查询类型信息
     */
    @RequestMapping("findAllSetmealss")
    public ResponseData findAllSetmealss(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query,@RequestParam(value = "tid") int tid){
        return ResponseData.success("返回套餐信息成功",  this.service.findAllSetmealss(pageNumber, size, query,tid));
    }
}
