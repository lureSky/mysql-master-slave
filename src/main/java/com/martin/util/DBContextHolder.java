package com.martin.util;

import com.martin.enums.DBTypeEnum;

/**
 * 数据源的操作类
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/9 0009
 */
public class DBContextHolder {
    //获取当前线程的上下文对象
    private static final ThreadLocal contextHolder = new ThreadLocal();

    /**
     * @Description: 设置数据源
     * @return:
     * @author: martin
     * @date: 2020-04-09 17:59
    */
    public static void setDbType(DBTypeEnum dbType){
        contextHolder.set(dbType.getValue());
    }

    /**
     * @Description: 获取当前数据源
     * @return:
     * @author: martin
     * @date: 2020-04-09 17:59
     */
    public static String getDbType(){
        return (String) contextHolder.get();
    }

    /**
     * @Description: 清除上下文对象
     * @return:
     * @author: martin
     * @date: 2020-04-09 17:59
     */
    public static void clearDbType(){
        contextHolder.remove();
    }
}
