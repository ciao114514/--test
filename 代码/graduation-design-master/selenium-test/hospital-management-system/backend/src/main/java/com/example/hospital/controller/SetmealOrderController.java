package com.example.hospital.controller;

import com.example.hospital.pojo.*;
import com.example.hospital.service.*;
import com.example.hospital.utils.ResponseData;
import com.example.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("setmealOrder")
public class SetmealOrderController {
    @Autowired
    private SetmealOrderService setmealorderService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ArrangeService arrangeService;
    @Autowired
    private OrderService orderService;

    /**
     * 添加医生订单
     * 当前用户id
     * 套餐tid 查询所有科医生 添加排号， 添加各科订单
     *
     */

    @RequestMapping("addOrders")
    @ResponseBody
    public ResponseData addOrders(Integer oId){
        SetmealOrders o = setmealorderService.selectSetmealOrderByOid(oId);
        System.out.println(o);
        Model model =new Model();
        model.setTid(o.getTid());
        model.setArtime(o.getOStart().substring(0, 10));
        List<SetmealDoctor> od =setmealorderService.selectSetmealDoctorById(model);
        System.out.println(od);
        try {
            for (SetmealDoctor sd : od) {

                Orders orders = new Orders();
                orders.setPId(o.getPId());
                orders.setDId(sd.getDid());
                orders.setOTotalPrice(sd.getChprice());
                orders.setOStart(o.getOStart());
                orders.setTid(o.getTid());
                orders.setTname(sd.getChname());
                orders.setPOid(oId);
                orders.setChid(sd.getChid());
                orders.setOtype("TT");
                orderService.addOrder2(orders, sd.getArid());
            }
            return ResponseData.success("缴费成功！" ,o);
        }catch (Exception e){



            return ResponseData.success("医生没有排版，请检查！");
        }

    }

    
    /**
     * 根据id设置缴费状态
     */
    @RequestMapping("updateSetmealOrdersPrice")
    public ResponseData updatePrice(int oId){
        if (this.setmealorderService.updatePrice(oId))
            return ResponseData.success("根据id设置缴费状态成功");
        return ResponseData.success("根据id设置缴费状态失败");
    }
    /**
     * 根据id设置缴费状态
     */
    @RequestMapping("updateSetmealOrdersPrice2")
    public ResponseData updatePrice2(int oId){
        if (this.setmealorderService.updatePrice2(oId))
            return ResponseData.success("根据id设置缴费状态成功");
        return ResponseData.success("根据id设置缴费状态失败");
    }

    /**
     * 增加预约
     */
    @RequestMapping("addSetmealOrders")
    @ResponseBody
    public ResponseData addOrder(SetmealOrders order){
        order.setOtype("T");
        if (this.setmealorderService.addSetmealOrders(order))
            return ResponseData.success("插入预约体检成功");
        return ResponseData.fail("插入预约体检信息失败");
    }


    /**
     * 创建一个订单信息
     * 生成单号 显示当前用户 套餐
     * 参数 日期 当前用户id 套餐tid
     * 当天的预约
     */
    @RequestMapping("selectSetmealOrder")
    @ResponseBody
    public ResponseData  selectSetmealOrder(@RequestParam(value = "pid") Integer pid ,
                                            @RequestParam(value = "tid") Integer tid,
                                            @RequestParam(value = "ostart") String ostart){
        Setmeal setmeal=null;
        Patient patient=null;
        SetmealOrders os = new SetmealOrders();
        os.setPId(pid);
        os.setTid(tid);
        os.setOStart(ostart);
        //查询 订单
       List<SetmealOrders> ls = setmealorderService.selectBypId_oStart_tid(os);
        System.out.println(ls);
       //判断是否有订单
        if(ls.size()==0){
             setmeal =  setmealService.selectById(tid);
             patient = patientService.findPatientById(pid);

            SetmealOrders orders = new SetmealOrders();
            orders.setTid(tid);
            orders.setTname(setmeal.getTname());
            orders.setPId(pid);
            orders.setOStart(ostart);
            orders.setOTotalPrice(setmeal.getPrice());
            orders.setOState(0);
            orders.setOPriceState(0);
            orders.setPatient(patient);
            orders.setSetmeal(setmeal);
            ls.add(orders);

        }

        return  ResponseData.success("根据日期查询套餐订单信息成功", ls);

    }

    /**
     * 创建一个订单信息
     * 生成单号 显示当前用户 套餐
     * 参数 日期 当前用户id 套餐tid
     * 当天的预约
     */
    @RequestMapping("selectAdminSetmealOrder")
    @ResponseBody
    public ResponseData  selectAdminSetmealOrder(@RequestParam(value = "otype") String otype,
                                                 @RequestParam(value = "tid") Integer tid,
                                            @RequestParam(value = "ostart") String ostart){

        //导入的
       List<SetmealOrders> od = setmealorderService.selectByotype(otype);
        List<SetmealOrders> ls = null;
        for (SetmealOrders d : od){
            Setmeal setmeal=null;
            Patient patient=null;
            SetmealOrders os = new SetmealOrders();
            os.setPId(d.getPId());
            os.setTid(tid);
            os.setOStart(ostart);
            //查询 订单
            ls = setmealorderService.selectBypId_oStart_tid(os);
            System.out.println(ls);
        }

        return  ResponseData.success("根据日期查询套餐订单信息成功", od);

    }


}
