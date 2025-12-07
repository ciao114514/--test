package com.example.hospital.controller;

import com.example.hospital.pojo.Doctor;
import com.example.hospital.pojo.Subject;
import com.example.hospital.service.SubjectService;
import com.example.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类型
 */
@RestController
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService service;

    /**
     * 添加类型
     * @param subject
     * @return
     */

    @RequestMapping("addSubject")
    public ResponseData addSubject(Subject subject){
        Boolean bo = service.addSubject(subject);

        if (bo) {
            return ResponseData.success("增加类型信息成功");
        }
        return ResponseData.fail("增加类型信息失败！账号或已被占用");
    }

    /**
     * 删除类型信息
     */
    @RequestMapping("deleteSubject")
    public ResponseData deleteSubject(@RequestParam(value = "kid") int kid) {
        Boolean bo = this.service.deleteSubject(kid);
        if (bo){
            return ResponseData.success("删除类型信息成功");
        }
        return ResponseData.fail("删除类型信息失败");
    }
    /**
     * 根据id查找类型
     */
    @RequestMapping("findSubject")
    public ResponseData findSubject(@RequestParam(value = "kid") int kid) {
        return ResponseData.success("查询类型成功", this.service.selectById(kid));
    }

    /**
     * 根据id查找类型
     */
    @RequestMapping("findSubjects")
    public ResponseData findSubjects() {
        return ResponseData.success("查询类型成功", this.service.selectSubjects());
    }


    /**
     * 修改类型信息
     * bug: dState会自动更新为0
     */
    @RequestMapping("modifySubject")
    @ResponseBody
    public ResponseData modifySubject(Subject subject) {
        this.service.modifySubject(subject);
        return ResponseData.success("修改类型信息成功");
    }
    /**
     * 分页模糊查询类型信息
     */
    @RequestMapping("findAllSubjects")
    public ResponseData findAllSubjects(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("返回类型信息成功",  this.service.findAllSubjects(pageNumber, size, query));
    }

}
