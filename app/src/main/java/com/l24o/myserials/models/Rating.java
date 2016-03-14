package com.l24o.myserials.models;

import io.realm.RealmObject;

/**
 * Created by chuff on 14.03.2016.
 */
public class Rating extends RealmObject {

    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
