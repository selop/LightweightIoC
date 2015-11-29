package com.selop.beans.subpackage;

import com.selop.annotation.Bean;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by selop on 29/11/15.
 */
@Bean
@Singleton
public class CircularDepBean {

    @Inject
    CircularDepChild child;

    public CircularDepChild getChild() {
        return child;
    }

    public void setChild(CircularDepChild child) {
        this.child = child;
    }
}
