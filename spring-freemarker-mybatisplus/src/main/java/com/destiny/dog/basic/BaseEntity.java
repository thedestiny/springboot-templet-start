package com.destiny.dog.basic;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseEntity implements Serializable {


    private static final long serialVersionUID = -3590100097725795171L;

    @Override
    public String toString() {
        String name = this.getClass().getName();
        return name  + " : to String -> "+ ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
