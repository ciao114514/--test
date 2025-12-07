package com.example.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.pojo.Model;
import com.example.hospital.pojo.Orders;
import com.example.hospital.pojo.SetmealDoctor;
import com.example.hospital.pojo.SetmealOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealOrderMapper extends BaseMapper<SetmealOrders> {

    List<SetmealDoctor> selectSetmealDoctorById(Model model);

    List<SetmealOrders>  selectBypId_oStart_tid(SetmealOrders os);

    List<SetmealOrders>   selectByotype(String otype);

    SetmealOrders findOrderByssOid(int oId);


}
