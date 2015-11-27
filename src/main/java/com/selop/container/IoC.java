package com.selop.container;

import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by selop on 23/11/15.
 */
public interface IoC {

    /**
     * Ask the container for a bean by passing a type.
     *
     * @param cls the type
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoBeanAnnotationException
     */
    <T> T resolve(Class cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException, BeanNotFoundException;

    /**
     * Ask the container for a bean by passing a @Named String.
     *
     * @param beanName
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoBeanAnnotationException
     */
    Object resolve(String beanName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException, BeanNotFoundException;

    /**
     * Add a bean to the container programmatically.
     *
     * @param cls The type to be added.
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoBeanAnnotationException
     */
    <T> T createInstance(Class<T> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException,InvocationTargetException, NoBeanAnnotationException, BeanNotFoundException;
}
