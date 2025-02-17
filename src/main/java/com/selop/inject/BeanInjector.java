package com.selop.inject;

import com.selop.annotation.Bean;
import com.selop.container.SimpleContainer;
import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Injector to perform DI for classes annotated with @Bean.
 */
public class BeanInjector implements Injector {

    private static Logger log = LoggerFactory.getLogger(BeanInjector.class);

    /**
     * If the given Bean is annotated with '@Bean' this method will loop through every field
     * and inject through setter-injection by looking up in the DI-dictionary.
     *
     * @param bean The bean which dependencies shall be injected.
     */
    public <T> void inject(T bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoBeanAnnotationException, BeanNotFoundException {

        SimpleContainer container = SimpleContainer.getInstance();

        Class<? extends Object> cls = bean.getClass();

        // Only inject into classes annotated with @Bean
        if(!cls.isAnnotationPresent(Bean.class))
            throw new NoBeanAnnotationException("The class " + cls.getCanonicalName() + " has no @Bean annotation.");

        for(Field field : getAllFields(cls)) {

            String fieldName = field.getName();
            String className = cls.getCanonicalName();

            log.debug("{}::{}: with @Inject annotation found. ", className, fieldName);

            Method setterMethod = null;
            try {
                // Assume, that only classes which match Bean conventions will be annotated with @Bean
                setterMethod = new PropertyDescriptor(fieldName, cls).getWriteMethod();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalAccessException("Error invoking method " + setterMethod.getName() + " on class " + className);
            }

            // TODO: 24/11/15
            // if @Named annotation is present at the field, then we find it via bean name
            // else via the referenced class name:
            Named named = field.getAnnotation(Named.class);
            Object injectedValue = (named != null) ? container.resolve(named.value()) : container.resolve(field.getType());

            try {
                setterMethod.invoke(bean, injectedValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("Injected into method {} for class {}", setterMethod.getName(), className);
        }

    }

    /**
     * Looks for @Inject annotations of fields for the class and it's parent super class.
     *
     * @param cls Given bean to perform the lookup.
     * @return List of fields annotated with @Inject
     */
    public List<Field> getAllFields(Class cls) {
        List<Field> fields = new ArrayList<Field>();

        for (Field f : cls.getDeclaredFields())
            if (f.isAnnotationPresent(Inject.class))
                fields.add(f);

        // recursively loop through the fields of superclasses
        Class superCls = cls.getSuperclass();
        if(superCls != null)
            fields.addAll( getAllFields(superCls) );

        return fields;
    }
}
