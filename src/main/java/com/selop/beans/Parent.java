package com.selop.beans;

import com.selop.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by selop on 25/11/15.
 */
@Singleton
@Bean
public class Parent {
    @Inject
    protected AnotherBean bean;

    public AnotherBean getBean() {
        return bean;
    }

    public void setBean(AnotherBean bean) {
        this.bean = bean;
    }
}
