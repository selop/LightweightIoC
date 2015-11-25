package com.selop.beans;

import com.selop.annotation.Bean;

import javax.inject.Singleton;

/**
 * Created by selop on 23/11/15.
 */
@Bean
@Singleton
public class Child extends Parent {

    // extending Parent Bean class for super field
}
