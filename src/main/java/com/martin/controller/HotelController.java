package com.martin.controller;

import com.martin.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/11 0011
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @RequestMapping("test")
    public void test(){
        hotelService.testDynamicDB();
    }
}
