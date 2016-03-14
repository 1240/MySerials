package com.l24o.myserials.models;

import io.realm.RealmObject;

/**
 * Created by chuff on 14.03.2016.
 */
public class TranslateTeam extends RealmObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
