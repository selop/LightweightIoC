package com.selop.beans;

import javax.inject.Inject;

/**
 * See {@code BeanInjectorTest}.
 */
public class NoAnnotationBean {
    private final MyBean dep;

    @Inject
    public NoAnnotationBean(MyBean dep) {
        this.dep = dep;
    }

    public MyBean getDep() {
        return dep;
    }
}
