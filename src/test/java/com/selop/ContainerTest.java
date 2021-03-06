package com.selop;

import com.selop.beans.AnotherBean;
import com.selop.beans.NoAnnotationBean;
import com.selop.beans.subpackage.CircularDepBean;
import com.selop.container.SimpleContainer;
import com.selop.exception.BeanNotFoundException;
import com.selop.exception.NoBeanAnnotationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

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
            fail("Expected NoBeanAnnotationException to be thrown");
        } catch (NoBeanAnnotationException e) {
            Assert.assertThat(e.getMessage(), is(bean.getClass().getCanonicalName() + " has not been flagged as @Bean"));
        }
    }

    @Test
    public void addingBeanRegistered()  throws Exception {
        SimpleContainer.getInstance().getRegisteredBeans().put(AnotherBean.class, null);
        SimpleContainer.getInstance().resolve(AnotherBean.class);

        Assert.assertFalse(SimpleContainer.getInstance().getRegisteredBeans().isEmpty());
        Assert.assertEquals(SimpleContainer.getInstance().getRegisteredBeans().size(),1);
    }

    @Test
    public void twoBeansWithIdenticalNameCauseException() {

    }

    /*
     *  Bean -- injectDependency --> Child
     *  Child extends Parent
     *  Parent -- injectDependency --> Bean
     */
    @Test
    public void circularDependencyLeadsToException() throws Exception {
        try {
            SimpleContainer.getInstance().getNamedBeans().put("CircularDepBean",CircularDepBean.class);
            SimpleContainer.getInstance().resolve("CircularDepBean");
            fail("Expected InstantiationException to be thrown");
        } catch (InstantiationException e) {
            Assert.assertThat(e.getMessage(), is("Circle detected for class " + CircularDepBean.class.getCanonicalName()));
        }
    }

    @Test
    public void beanAliasNotFoundLeadsToException() throws Exception {
        try {
            SimpleContainer.getInstance().resolve("Alias");
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
        Assert.assertTrue(instance.resolve(alias) instanceof AnotherBean);
    }
}
