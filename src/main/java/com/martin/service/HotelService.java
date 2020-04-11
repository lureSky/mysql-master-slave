package com.martin.service;

import com.martin.entity.BaseInfo;
import com.martin.entity.UserInfo;
import com.martin.enums.DBTypeEnum;
import com.martin.mapper.BaseInfoMapper;
import com.martin.mapper.UserInfoMapper;
import com.martin.util.DBContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/11 0011
 */
@Slf4j
@Service
public class HotelService {

    @Resource
    private BaseInfoMapper baseInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    public void testDynamicDB(){
        //使用db1
        DBContextHolder.setDbType(DBTypeEnum.master);
        BaseInfo baseInfo = baseInfoMapper.selectById(1);
        int baseCount = baseInfoMapper.listCount();

        log.info("db1====================baseInfo:{},count:{}",baseInfo,baseCount);

        //使用db2
        DBContextHolder.setDbType(DBTypeEnum.slave);
        UserInfo userInfo = userInfoMapper.selectById(1);
        int userCount = userInfoMapper.listCount();

        log.info("slave====================userInfo:{},userCount:{}",userInfo,userCount);
    }
}
