package com.selop.beans;

import com.selop.annotation.Bean;

/**
 * See the {@code BeanInjectorTest} class.
 */
@Bean
public class NoInjectFieldsBean {
    private MyBean myField;

    public MyBean getMyField() {
        return myField;
    }

    public void setMyField(MyBean myField) {
        this.myField = myField;
    }
}
