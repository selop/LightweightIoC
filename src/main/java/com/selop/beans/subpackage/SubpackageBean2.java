package com.selop.beans.subpackage;

import com.selop.annotation.Bean;

import javax.inject.Singleton;

/**
 * Created by selop on 27/11/15.
 */
@Bean
@Singleton
public class SubpackageBean2 {
    SubpackageBean dep;

    public SubpackageBean getDep() {
        return dep;
    }

    public void setDep(SubpackageBean dep) {
        this.dep = dep;
    }
}
