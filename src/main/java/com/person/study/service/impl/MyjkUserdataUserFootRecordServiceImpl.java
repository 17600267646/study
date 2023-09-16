package com.person.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.person.study.entity.MyjkUserdataUserFootRecord;
import com.person.study.mapper.MyjkUserdataUserFootRecordMapper;
import com.person.study.service.MyjkUserdataUserFootRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户步数记录表 服务实现类
 * </p>
 *
 * @author gaojuhang
 * @since 2023-09-03
 */
@Service
@Slf4j
public class MyjkUserdataUserFootRecordServiceImpl extends ServiceImpl<MyjkUserdataUserFootRecordMapper, MyjkUserdataUserFootRecord> implements MyjkUserdataUserFootRecordService {
    @Resource
    private MyjkUserdataUserFootRecordMapper myjkUserdataUserFootRecordMapper;

    @Override
    public MyjkUserdataUserFootRecord testLog() {
        log.info("进入service了");
        return myjkUserdataUserFootRecordMapper.selectOne(new QueryWrapper<>());
    }
}
