package com.selop.container;

import com.selop.exception.NoBeanAnnotationException;
import com.selop.inject.BeanInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by selop on 23/11/15.
 */
public class SimpleContainer implements IoC {

    Logger log = LoggerFactory.getLogger(getClass());

    private Map<Class, Object> registeredBeans = new HashMap<Class, Object>();

    private Map<Class, Class> interfaces = new HashMap<Class, Class>();

    private Set<Class> objectsCurrentlyInitializing = new HashSet<Class>();

    private BeanInjector beanInjector = new BeanInjector();

    // Singleton pattern
    private static SimpleContainer instance = new SimpleContainer();

    public static SimpleContainer getInstance(){
        return instance;
    }

    public <T> T register(Class<T> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException,InvocationTargetException, NoBeanAnnotationException {

        String clsName = cls.getCanonicalName();
        log.info("Register bean : " + clsName);

        T newInstance;

        if (cls.isInterface()) {
            Class clsImpl = interfaces.get(cls); // TODO check if get of List already throws exception and will never return null
            String iName = clsImpl.getCanonicalName();
            if (clsImpl == null)
                throw new InstantiationException("No bean is known to implement the " + clsName + " interface");

            log.debug("Injecting class " + iName + " in place of the " + clsName + " interface" );

            cls = clsImpl;
        }

        newInstance = cls.newInstance();

        if (objectsCurrentlyInitializing.contains(cls))
            throw new InstantiationException("Circle detected for class " + cls.getCanonicalName());

        try {
            objectsCurrentlyInitializing.add(cls);
            log.debug("Injecting beans..");
            beanInjector.inject(newInstance);
        } finally {
            objectsCurrentlyInitializing.remove(cls);
        }
        return newInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException {

        T bean = null;

        if ( registeredBeans.containsKey(type) ) {
            Object obj = registeredBeans.get(type);

            if ( obj == null ){
                obj = register(type);
                registeredBeans.put(type, obj);
            }
            bean = (T) obj;

        } else {
            bean = (T) register(type);
        }

        return bean;
    }

    public Object resolve(String value) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoBeanAnnotationException{
        Class cls = namedBeans.get(value);

        if ( cls == null )
            throw new InstantiationException("Given bean name : " + value + " was not found.");

        return resolve(cls);
    }

    public Map<Class, Object> getRegisteredBeans() {
        return registeredBeans;
    }

    public Map<String, Class> getNamedBeans() {
        return namedBeans;
    }

    private Map<String, Class> namedBeans = new HashMap<String, Class>();

    public Map<Class, Class> getInterfaces() {
        return interfaces;
    }


}
