package com.yeyun.yeyunpush.common.utils;

import org.springframework.util.Assert;

/**
 * <p>
 *生成随机数
 * </p>
 *
 * @创建人 zhaozq
 * @创建时间 2019/8/20
 */
public class RandomUtil {

    /**
     *@描述   生成指定位数的数字字符串
     *@创建人 zhaozq
     *@创建时间 2019/8/20
     */
    public static String getRandomStr(int length) {
        Assert.notNull(length,"length not is null");
        Assert.isTrue(length > 0,"length must gt 0");
        return String.valueOf((int)((Math.random()*9+1)*Math.pow(10,length-1)));
    }
}
