package com.person.study.controller;

import cn.hutool.json.JSONUtil;
import com.person.study.entity.req.MqReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mq")
@Slf4j
public class ProducerMqController {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/send")
    public String sendMsg(@RequestBody MqReq req) {
        log.info("/mq/send请求参数为={}",JSONUtil.toJsonStr(req));
        rocketMQTemplate.convertAndSend("TopicTest", JSONUtil.toJsonStr(req).getBytes());


        return "Message sent";
    }

}