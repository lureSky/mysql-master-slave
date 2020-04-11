package com.martin.enums;

/**
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/9 0009
 */
public enum DBTypeEnum {
    master("master"),
    slave("slave");

    private String value;
    DBTypeEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
