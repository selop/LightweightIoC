package com.selop.beans.subpackage;

import com.selop.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Bean
@Singleton
@Named("Subpackage")
public class SubpackageBean2 implements SubpackageBeanInterface {

    @Inject
    @Named("Subpackage")
    public SubpackageBean bean;
}
