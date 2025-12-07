package com.example.hospital.controller;

import com.example.hospital.pojo.Arrange;
import com.example.hospital.pojo.Doctor;
import com.example.hospital.service.ArrangeService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/arrange")
public class ArrangeController {
    @Autowired
    private ArrangeService arrangeService;
    @Autowired
    private DoctorService doctorService;

    /**
     * 一件添加排版
     */
    @RequestMapping("addChecks")
    @ResponseBody
    public ResponseData addChecks(String arTime) {
        List<Doctor> ds = doctorService.findAll();
        for (Doctor d:ds) {
            if (d.getdId()!=-1){
                Arrange arrange = new Arrange();
                arrange.setArId(d.getdId()+arTime);
                arrange.setArTime(arTime);
                arrange.setdId(d.getdId());
                this.arrangeService.addArrange(arrange);
            }

        }
        return ResponseData.success("增加检查信息成功");
    }

    /**
     * 根据日期查询排班信息
     */
    @RequestMapping("findByTime")
    public ResponseData findByTime(@RequestParam(value = "arTime") String arTime, @RequestParam(value = "dSection") String dSection) {
        return ResponseData.success("根据日期查询排班信息成功", this.arrangeService.findByTime(arTime, dSection));
    }
    /**
     * 增加排班信息
     */
    @RequestMapping("addArrange")
    public ResponseData addArrange(Arrange arrange){
        if (this.arrangeService.addArrange(arrange))
            return ResponseData.success("增加排班信息成功");
        return ResponseData.fail("该医生该日已排班");
    }

    /**
     * 删除排班信息
     */
    @RequestMapping("deleteArrange")
    public ResponseData deleteArrange(String arId){
        if (this.arrangeService.deleteArrange(arId))
            return ResponseData.success("删除排班信息成功");
        return ResponseData.fail("排班信息不存在");
    }

}
