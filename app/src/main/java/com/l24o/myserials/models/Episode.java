package com.l24o.myserials.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by l24o on 18.03.16.
 */
public class Episode extends RealmObject {

    private String name;

    @PrimaryKey
    private String code;
    private String rating;
    private Date date;
    private RealmList<TranslateTeam> translateTeams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<TranslateTeam> getTranslateTeams() {
        return translateTeams;
    }

    public void setTranslateTeams(RealmList<TranslateTeam> translateTeams) {
        this.translateTeams = translateTeams;
    }
}
