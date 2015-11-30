package com.selop.beans;

import com.selop.annotation.Bean;

import javax.inject.Singleton;

/**
 * Testing circular dependencies.
 *
 * @author selop
 */
@Bean
@Singleton
public class Child extends Parent {
    // extending Parent Bean class for super field
}
