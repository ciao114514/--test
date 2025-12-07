package com.example.hospital.controller;

import com.example.hospital.pojo.Checks;
import com.example.hospital.pojo.Doctor;
import com.example.hospital.service.CheckService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("check")
public class CheckController {
    @Autowired
    private CheckService checkService;

    @Autowired
    private DoctorService doctorService;




    /**
     * 根据kname查找类型
     */
    @RequestMapping("findChecksBykname")
    public ResponseData findChecks(String kname) {
        System.out.println(kname);
        return ResponseData.success("查询类型成功", this.checkService.selectBykname(kname));
    }


    /**
     * 根据id查找类型
     */
    @RequestMapping("findChecks")
    public ResponseData findChecks(Integer tid) {
        return ResponseData.success("查询类型成功", this.checkService.selectsssChecks(tid));
    }


    /**
     * 分页模糊查询所有检查信息
     */
    @RequestMapping("findAllChecks")
    public ResponseData findAllChecks(int pageNumber, int size, String query,String chId){

        return ResponseData.success("返回所有检查信息成功", this.checkService.findAllChecks(pageNumber, size, query,chId));
    }
    /**
     * 根据id查找检查
     */
    @RequestMapping("findCheck")
    public ResponseData findCheck(int chId){
        return ResponseData.success("根据id查找检查成功", this.checkService.findCheck(chId));
    }
    /**
     * 增加检查信息
     */
    @RequestMapping("addCheck")
    @ResponseBody
    public ResponseData addCheck(Checks checks) {
        Boolean bo = this.checkService.addCheck(checks);
        Doctor d = new Doctor();
        d.setdSection(checks.getKname());
        d.setdPrice(checks.getChPrice());
        this.doctorService.updatedoctor(d);

        if (bo) {
            return ResponseData.success("增加检查信息成功");
        }
        return ResponseData.fail("增加检查信息失败！账号或已被占用");
    }
    /**
     * 删除药物信息
     */
    @RequestMapping("deleteCheck")
    public ResponseData deleteCheck(@RequestParam(value = "chId") int chId) {
        Boolean bo = this.checkService.deleteCheck(chId);
        if (bo){
            return ResponseData.success("删除检查信息成功");
        }
        return ResponseData.fail("删除检查信息失败");
    }
    /**
     * 修改检查信息
     */
    @RequestMapping("modifyCheck")
    @ResponseBody
    public ResponseData modifyCheck(Checks checks) {
        this.checkService.modifyCheck(checks);
        Doctor d = new Doctor();
        d.setdSection(checks.getKname());
        d.setdPrice(checks.getChPrice());
        this.doctorService.updatedoctor(d);
        return ResponseData.success("修改检查项目信息成功");
    }

}
