package com.selop;

import com.selop.beans.Child;
import com.selop.beans.MyBean;
import com.selop.beans.NoAnnotationBean;
import com.selop.beans.NoInjectFieldsBean;
import com.selop.container.SimpleContainer;
import com.selop.exception.NoBeanAnnotationException;
import com.selop.inject.BeanInjector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * See {@code BeanInjector} class.
 */
class BeanInjectorTest {

    @AfterEach
    public void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    void noBeanAnnotationShouldLeadToNoBeanAnnoException() {
        NoAnnotationBean bean = new NoAnnotationBean(new MyBean());
        BeanInjector injector = new BeanInjector();
        
        NoBeanAnnotationException exception = assertThrows(NoBeanAnnotationException.class, () -> {
            injector.inject(bean);
        });
        
        assertThat(exception.getMessage(), is("The class "+ bean.getClass().getCanonicalName() + " has no @Bean annotation."));
    }

    @Test
    void noInjectAnnotatedFieldsShouldLeaveEmptyFieldsArray() {
        NoInjectFieldsBean bean = new NoInjectFieldsBean();
        BeanInjector injector = new BeanInjector();

        assertTrue(injector.getAllFields(bean.getClass()).isEmpty());
    }

    @Test
    void fieldArrayShouldContainParentInjectionFields() {
        Child bean = new Child(); // child itself has no @Inject fields, but the parent Class
        BeanInjector injector = new BeanInjector();
        List<Field> fields = injector.getAllFields(bean.getClass());
        assertFalse(fields.isEmpty());
    }
}
