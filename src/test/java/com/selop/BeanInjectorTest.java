package com.selop;

import com.selop.beans.Child;
import com.selop.beans.NoAnnotationBean;
import com.selop.beans.NoInjectFieldsBean;
import com.selop.container.SimpleContainer;
import com.selop.exception.NoBeanAnnotationException;
import com.selop.inject.BeanInjector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

/**
 * See {@code BeanInjector} class.
 */
public class BeanInjectorTest {

    @After
    public void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    public void noBeanAnnotationShouldLeadToNoBeanAnnoException() throws Exception {
        NoAnnotationBean bean = new NoAnnotationBean();
        BeanInjector injector = new BeanInjector();
        try {
            injector.inject(bean);
            fail("Expected NoBeanAnnotationException to be thrown");
        } catch (NoBeanAnnotationException e) {
            Assert.assertThat(e.getMessage(), is("The class "+ bean.getClass().getCanonicalName() + " has no @Bean annotation."));
        }
    }

    @Test
    public void noInjectAnnotatedFieldsShouldLeaveEmptyFieldsArray() {
        NoInjectFieldsBean bean = new NoInjectFieldsBean();
        BeanInjector injector = new BeanInjector();

        Assert.assertTrue(injector.getAllFields(bean.getClass()).isEmpty());
    }

    @Test
    public void fieldArrayShouldContainParentInjectionFields() {
        Child bean = new Child(); // child itself has no @Inject fields, but the parent Class
        BeanInjector injector = new BeanInjector();
        List<Field> fields = injector.getAllFields(bean.getClass());
        Assert.assertFalse(fields.isEmpty());
    }
}
