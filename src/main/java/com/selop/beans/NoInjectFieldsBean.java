package com.selop.beans;

import com.selop.annotation.Bean;

/**
 * Created by selop on 25/11/15.
 */
@Bean
public class NoInjectFieldsBean {
    private MyBean myField;

    public MyBean getMyField() {
        return myField;
    }

    public void setMyField(MyBean myField) {
        this.myField = myField;
    }
}
