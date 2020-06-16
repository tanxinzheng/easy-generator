package com.xmomen.module.test;

import net.sf.cglib.beans.BeanCopier;

/*
 * @Author tanxinzheng
 * @Description TODO
 * @Date 2020/6/16
 */
public class BeanCopierUtils {

    public static <T> T copy(Object source, Class<T> clazz){
        if(source == null){
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), clazz, false);
        T target = (T) clazz.getConstructors();
        beanCopier.copy(source, target, null);
        return target;
    }

}
