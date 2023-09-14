package com.person.study.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.person.study.entity.resp.MyRedisModel;
import com.person.study.service.MyjkUserdataUserFootRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户步数记录表 前端控制器
 * </p>
 *
 * @author gaojuhang
 * @since 2023-09-03
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private MyjkUserdataUserFootRecordService myjkUserdataUserFootRecordService;
    @Autowired
    private RedisTemplate redisTemplate;



    @GetMapping("/myredis")
    public MyRedisModel myredis() {


        redisTemplate.opsForHash().putAll("test-hash", MapUtil.builder(String.valueOf(RandomUtil.randomInt(0, 10000)), randomChinese()).build());
        Set keys = redisTemplate.keys("*");
        Map mapVal = redisTemplate.opsForHash().entries("test-hash");
        MyRedisModel resp = MyRedisModel.builder().stringSet(keys).map(mapVal).build();
        return resp;
    }


    public String randomChinese() {
        int length = 5; // 生成的随机汉字长度

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = RandomUtil.randomChar(RandomUtil.BASE_CHAR);
            sb.append(randomChar);
        }
        return sb.toString();

    }

}

