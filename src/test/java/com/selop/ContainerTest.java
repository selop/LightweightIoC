package com.selop;

import com.selop.beans.AnotherBean;
import com.selop.beans.MyBean;
import com.selop.beans.NoAnnotationBean;
import com.selop.beans.subpackage.CircularDepBean;
import com.selop.container.SimpleContainer;
import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See {@code SimpleContainer} class.
 */
class ContainerTest {

    @AfterEach
    void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    void addingBeanWithoutAnnotationLeadsToException() {
        NoAnnotationBean bean = new NoAnnotationBean(new MyBean());
        
        NoBeanAnnotationException exception = assertThrows(NoBeanAnnotationException.class, () -> {
            SimpleContainer.getInstance().createInstance(bean.getClass());
        });
        
        assertThat(exception.getMessage(), is(bean.getClass().getCanonicalName() + " has not been flagged as @Bean"));
    }

    @Test
    void addingBeanRegistered() throws Exception {
        SimpleContainer.getInstance().getRegisteredBeans().put(AnotherBean.class, null);
        SimpleContainer.getInstance().resolve(AnotherBean.class);

        assertFalse(SimpleContainer.getInstance().getRegisteredBeans().isEmpty());
        assertEquals(1, SimpleContainer.getInstance().getRegisteredBeans().size());
    }

    @Test
    void twoBeansWithIdenticalNameCauseException() {

    }

    /*
     *  Bean -- injectDependency --> Child
     *  Child extends Parent
     *  Parent -- injectDependency --> Bean
     */
    @Test
    void circularDependencyLeadsToException() {
        SimpleContainer.getInstance().getNamedBeans().put("CircularDepBean",CircularDepBean.class);
        
        InstantiationException exception = assertThrows(InstantiationException.class, () -> {
            SimpleContainer.getInstance().resolve("CircularDepBean");
        });
        
        assertThat(exception.getMessage(), is("Circle detected for class " + CircularDepBean.class.getCanonicalName()));
    }

    @Test
    void beanAliasNotFoundLeadsToException() {
        BeanNotFoundException exception = assertThrows(BeanNotFoundException.class, () -> {
            SimpleContainer.getInstance().resolve("Alias");
        });
        
        assertThat(exception.getMessage(), is("Given bean name : " + "Alias" + " was not found."));
    }

    @Test
    void beanAliasIsTheProperInstance() throws Exception {
        SimpleContainer instance = SimpleContainer.getInstance();
        final String alias = "Alias";

        instance.getNamedBeans().put(alias,AnotherBean.class);
        assertTrue(instance.resolve(alias) instanceof AnotherBean);
    }
}
