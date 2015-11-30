package com.selop.beans;

import com.selop.annotation.Bean;

import javax.inject.Named;

/**
 * Testing @Named annotation in conjunction with the IoC container.
 *
 * @author selop
 */
@Bean
@Named("Alias")
public class AnotherBean {
}
