package com.selop;

import com.selop.beans.AnotherBean;
import com.selop.beans.MyBean;
import com.selop.beans.NoAnnotationBean;
import com.selop.container.SimpleContainer;
import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

/**
 * See {@code SimpleContainer} class.
 */
public class ContainerTest {

    @After
    public void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    public void addingBeanWithoutAnnotationLeadsToException() throws Exception {
        NoAnnotationBean bean = new NoAnnotationBean();
        try {
            SimpleContainer.getInstance().createInstance(bean.getClass());
            fail("Expected NoBeanAnnotaionException to be thrown");
        } catch (NoBeanAnnotationException e) {
            Assert.assertThat(e.getMessage(), is(bean.getClass().getCanonicalName() + " has not been flagged as @Bean"));
        }
    }

    @Test
    public void addingBeanRegistered() {
        try {
            SimpleContainer.getInstance().getRegisteredBeans().put(AnotherBean.class, null);
            SimpleContainer.getInstance().resolve(AnotherBean.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoBeanAnnotationException e) {
            e.printStackTrace();
        } catch (BeanNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(SimpleContainer.getInstance().getRegisteredBeans().isEmpty());
        Assert.assertEquals(SimpleContainer.getInstance().getRegisteredBeans().size(),1);
    }

    @Test
    public void twoBeansWithIdenticalNameCauseException() {
        // TODO: 27/11/15
    }

    /*
     *  MyBean -- injectDependency --> Child
     *  Child extends Parent
     *  Parent -- injectDependency --> MyBean
     *
     *  todo change Parent field to MyBean for test, automate switch?
     */
    @Test
    @Ignore
    public void circularDependencyLeadsToException () throws Exception {
        try {
            SimpleContainer.getInstance().getNamedBeans().put("MyBean",MyBean.class);
            SimpleContainer.getInstance().resolve("MyBean");
            fail("Expected InstantiationException to be thrown");
        } catch (InstantiationException e) {
            Assert.assertThat(e.getMessage(), is("Circle detected for class " + MyBean.class.getCanonicalName()));
        }
    }

    @Test
    public void beanAliasNotFoundLeadsToException () throws Exception {
        SimpleContainer instance = SimpleContainer.getInstance();

        try {
            instance.resolve("Alias");
            fail("Expected BeanNotFoundException to be thrown");
        } catch (BeanNotFoundException e) {
            Assert.assertThat(e.getMessage(), is("Given bean name : " + "Alias" + " was not found."));
        }
    }

    @Test
    public void beanAliasIsTheProperInstance() throws Exception {
        SimpleContainer instance = SimpleContainer.getInstance();
        final String alias = "Alias";

        instance.getNamedBeans().put(alias,AnotherBean.class);
        try {
            Assert.assertTrue(instance.resolve(alias) instanceof AnotherBean);
        } catch (BeanNotFoundException e) {
            e.printStackTrace();
        }
    }
}
