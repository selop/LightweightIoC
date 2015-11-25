package com.selop.container;

import com.selop.exception.NoBeanAnnotationException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by selop on 23/11/15.
 */
public interface IoC {

    <T> T resolve(Class cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException;

    Object resolve(String beanName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException;
}
