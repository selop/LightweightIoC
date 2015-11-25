package com.selop.beans;

import com.selop.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by selop on 23/11/15.
 */
@Bean
@Named("MyBean")
public class MyBean {

    public MyBean() {
    }

    @Inject
    @Named("kindchen")
    Child child;

    @Inject
    AnotherBean anotherBean;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
    public AnotherBean getAnotherBean() {
        return anotherBean;
    }

    public void setAnotherBean(AnotherBean anotherBean) {
        this.anotherBean = anotherBean;
    }

}
