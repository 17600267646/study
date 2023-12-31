package com.person.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.person.study.code.ApiResponse;
import com.person.study.entity.MyjkUserdataUserFootRecord;
import com.person.study.service.MyjkUserdataUserFootRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@Slf4j
public class MybatisPlusController {

    @Autowired
    private MyjkUserdataUserFootRecordService myjkUserdataUserFootRecordService;

    @GetMapping("/hello")
    public ApiResponse hello() {
        List<MyjkUserdataUserFootRecord> list = myjkUserdataUserFootRecordService.list(new QueryWrapper<>());

        return ApiResponse.builder().code(200).message("success").data(list).build();
    }


    @GetMapping("/getByStatus")
    public ApiResponse getByStatus(HttpServletRequest httpServletRequest, @RequestParam("status") Integer status) {
        MyjkUserdataUserFootRecord one = myjkUserdataUserFootRecordService.getOne(new QueryWrapper<MyjkUserdataUserFootRecord>().lambda().eq(MyjkUserdataUserFootRecord::getStatus, status));


        return ApiResponse.builder().code(200).message("success").data(one).build();
    }


    @GetMapping("/testLog")
    public ApiResponse testLog(HttpServletRequest httpServletRequest, @RequestParam("status") Integer status) {
        MyjkUserdataUserFootRecord one = myjkUserdataUserFootRecordService.testLog(status);


        return ApiResponse.builder().code(200).message("success").data(one).build();
    }


}

