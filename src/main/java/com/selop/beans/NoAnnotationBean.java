package com.selop.beans;

import javax.inject.Inject;

/**
 * See {@code BeanInjectorTest}.
 */
public class NoAnnotationBean {
    @Inject
    MyBean dep;

    public MyBean getDep() {
        return dep;
    }

    public void setDep(MyBean dep) {
        this.dep = dep;
    }
}
