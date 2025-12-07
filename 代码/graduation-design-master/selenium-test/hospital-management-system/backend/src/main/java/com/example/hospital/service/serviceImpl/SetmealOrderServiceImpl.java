package com.example.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hospital.mapper.SetmealOrderMapper;
import com.example.hospital.pojo.Model;
import com.example.hospital.pojo.SetmealDoctor;
import com.example.hospital.pojo.SetmealOrders;
import com.example.hospital.service.SetmealOrderService;
import com.example.hospital.utils.RandomUtil;
import com.example.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("SetmealOrderService")
public class SetmealOrderServiceImpl implements SetmealOrderService {

    @Resource
    private SetmealOrderMapper setmealOrderMapper;
    @Autowired
    private JedisPool jedisPool;//redis连接池

    @Override
    public List<SetmealDoctor> selectSetmealDoctorById(Model model) {
        return setmealOrderMapper.selectSetmealDoctorById(model);
    }

    @Override
    public SetmealOrders selectSetmealOrderByOid(int oId) {
        return setmealOrderMapper.selectById(oId);
    }

    @Override
    public List<SetmealOrders> selectBypId_oStart_tid(SetmealOrders os) {
        return this.setmealOrderMapper.selectBypId_oStart_tid(os);
    }


    @Override
    public Boolean addSetmealOrders(SetmealOrders order) {
        order.setOTprice(0);
        order.setOPstate(0);
        order.setOId(RandomUtil.randomOid(order.getPId()));
        this.setmealOrderMapper.insert(order);
        return true;
    }

    /**
     * 根据id设置缴费状态
     */
    @Override
    public Boolean updatePrice(int oId){
        /**
         * 用QueryWrapper如果不把外键的值也传进来，会报错
         * 用UpdateWrapper就正常
         */
        UpdateWrapper<SetmealOrders> wrapper = new UpdateWrapper<>();
        wrapper.eq("o_id", oId).set("o_price_state", 1);
        int i = this.setmealOrderMapper.update(null, wrapper);
        System.out.println("影响行数"+i);
        return true;
    }
    /**
     * 根据id设置缴费状态
     */
    @Override
    public Boolean updatePrice2(int oId){
        /**
         * 用QueryWrapper如果不把外键的值也传进来，会报错
         * 用UpdateWrapper就正常
         */
        UpdateWrapper<SetmealOrders> wrapper = new UpdateWrapper<>();
        wrapper.eq("o_id", oId).set("o_pstate", 1);
        int i = this.setmealOrderMapper.update(null, wrapper);
        System.out.println("影响行数"+i);
        return true;
    }

    @Override
    public List<SetmealOrders> selectByotype(String otype) {
        return setmealOrderMapper.selectByotype(otype);
    }

    @Override
    public SetmealOrders findOrderByssOid(int oId) {
        return  this.setmealOrderMapper.findOrderByssOid(oId);
    }
}
