package com.selop.inject;

import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface for injecting dependencies into classes.
 *
 * @author selop
 */
public interface Injector {

    /**
     * Injecting into a given bean.
     *
     * @param bean The bean which fields shall be injected.
     */
    <T> void inject(T bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoBeanAnnotationException, BeanNotFoundException;
}
