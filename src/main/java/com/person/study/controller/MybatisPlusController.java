package com.person.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.person.study.code.ApiResponse;
import com.person.study.entity.MyjkUserdataUserFootRecord;
import com.person.study.service.MyjkUserdataUserFootRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户步数记录表 前端控制器
 * </p>
 *
 * @author gaojuhang
 * @since 2023-09-03
 */
@RestController
@RequestMapping("/myjk")
public class MybatisPlusController {

    @Autowired
    private MyjkUserdataUserFootRecordService myjkUserdataUserFootRecordService;

    @GetMapping("/hello")
    public ApiResponse hello() {
        List<MyjkUserdataUserFootRecord> list = myjkUserdataUserFootRecordService.list(new QueryWrapper<>());

        return ApiResponse.builder().code(200).message("success").data(list).build();
    }



}

