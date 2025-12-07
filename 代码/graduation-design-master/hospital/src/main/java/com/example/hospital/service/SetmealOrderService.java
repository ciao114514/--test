package com.example.hospital.service;

import com.example.hospital.pojo.Model;
import com.example.hospital.pojo.Orders;
import com.example.hospital.pojo.SetmealDoctor;
import com.example.hospital.pojo.SetmealOrders;

import java.util.HashMap;
import java.util.List;

public interface SetmealOrderService {

    List<SetmealDoctor> selectSetmealDoctorById(Model model);

    SetmealOrders selectSetmealOrderByOid(int oId);

    List<SetmealOrders>  selectBypId_oStart_tid(SetmealOrders os);

    Boolean addSetmealOrders(SetmealOrders order);
    /**
     * 根据id设置缴费状态
     */
    Boolean updatePrice(int oId);
    Boolean updatePrice2(int oId);

    List<SetmealOrders>   selectByotype(String otype);

    SetmealOrders findOrderByssOid(int oId);
}
