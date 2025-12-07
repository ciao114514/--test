package com.example.hospital.service.serviceImpl;

import com.example.hospital.mapper.ArrMapper;
import com.example.hospital.pojo.Arr;
import com.example.hospital.service.ArrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("ArrService")
public class ArrServiceImpl implements ArrService {
    @Resource
    private ArrMapper arrMapper;
    @Autowired
    private JedisPool jedisPool;//redis连接池

    /**
     * 根据日期查询排班信息
     */
    @Override
    public List<Arr> findByTime(String arTime, String dSection) {
        return this.arrMapper.findByTime(arTime, dSection);
    }




    /**
     * 增加排班信息
     */
    public Boolean addArr(Arr arr){
        System.out.println(arr);
        Arr arrange1 = this.arrMapper.selectById(arr.getAid());
        Jedis jedis = jedisPool.getResource();
        HashMap<String, String> map = new HashMap<>();
        map.put("eTOn","400");
        map.put("nTOt","400");

        if (arrange1 == null) {
            //redis操作开始
//            jedis.hset(arrange.getArId(), map);
            // 或者使用hmset设置整个哈希表的值
            jedis.hmset(arr.getAid(), map);
            jedis.expire(arr.getAid(), 604800);
            //redis操作结束
            this.arrMapper.insert(arr);
            return true;
        }
        return false;
    }

    /**
     * 删除排班信息
     */
    public Boolean deleteArr(String arId){
        Arr arrange = this.arrMapper.selectById(arId);
        Jedis jedis = jedisPool.getResource();
        if (arrange != null) {
            jedis.del(arId);
            this.arrMapper.deleteById(arId);
            return true;
        }
        return false;
    }

}
