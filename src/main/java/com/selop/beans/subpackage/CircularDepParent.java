package com.selop.beans.subpackage;

import com.selop.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by selop on 29/11/15.
 */
@Bean
@Singleton
public class CircularDepParent {
    @Inject
    CircularDepBean circularDependency;

    public CircularDepBean getCircularDependency() {
        return circularDependency;
    }

    public void setCircularDependency(CircularDepBean circularDependency) {
        this.circularDependency = circularDependency;
    }
}
