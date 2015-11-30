package com.selop.container;

import com.selop.annotation.Bean;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Scans a given package for annotated classes. The {@code IoC} container will take care of instantiating those.
 */
public class PackageScanner {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private static SimpleContainer container = SimpleContainer.getInstance();

    private Set<Class<?>> beans;
    /**
     * Scans a given package for classes with @Bean annotations and populates the IoC Container.
     *
     * @param packagePath Root of a java package as a String
     */
    public void scan (String packagePath) {

        Reflections reflections = new Reflections(packagePath);
        beans = reflections.getTypesAnnotatedWith(Bean.class);

        beans.forEach(bean -> {
            if (bean.isAnnotationPresent(Singleton.class) || bean.isAnnotationPresent(Named.class)){

                Named named = bean.getAnnotation(Named.class);
                String name = named != null ? named.value() : null;

                if (name == null || name.length() == 0)
                    name = bean.getSimpleName();

                scanNamedBeans(bean, name);

                scanSingletonBean(bean, name);

                scanInterfaces(bean);
            }
        });
    }

    private void scanInterfaces(Class<?> bean) {
        List<Class<?>> interfaces = Arrays.asList(bean.getInterfaces());
        Map<Class, Class> interfaceMappings = container.getInterfaces();

        interfaces.forEach(iface -> {
            Class alreadyBound = interfaceMappings.get(iface);
            if (alreadyBound == null)
                interfaceMappings.put(iface, bean);
            else
                log.debug("Interface " + iface + " already bound to class " + bean.getCanonicalName());
        });
    }

    private void scanSingletonBean(Class<?> bean, String name) {
        if (bean.isAnnotationPresent(Singleton.class)) {
            container.getRegisteredBeans().put(bean, null);
            log.info("Singleton-Bean : " + bean.getCanonicalName()+ " with @Singleton annotation registered.");
        }
    }

    private void scanNamedBeans(Class<?> bean, String name) {
        if (container.getNamedBeans().get(name) != null)
            throw new RuntimeException("Double definition of entity: " + name);

        if(bean.isAnnotationPresent(Singleton.class)){
            container.getNamedBeans().put(name,bean);
            log.info("Bean : " + name +" with @Name annotation is registered.");
        }
    }

    public Set<Class<?>> getBeans() {
        return beans;
    }
}
