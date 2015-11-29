package com.selop.beans.subpackage;

import com.selop.annotation.Bean;

import javax.inject.Singleton;

/**
 * Created by selop on 29/11/15.
 */
@Bean
@Singleton
public class CircularDepChild extends CircularDepParent {
    // no local deps, inherited dependency in CircularDepParent
}
