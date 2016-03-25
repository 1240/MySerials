package com.l24o.myserials.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chuff on 14.03.2016.
 */
public class Serial extends RealmObject {

    @PrimaryKey
    private String code;
    private String name;
    private String url;
    private String status;
    private String chanel;
    private String genre;
    private String startDate;
    private String info;
    private RealmList<Rating> rating;
    private RealmList<TranslateTeam> translateTeam;
    private RealmList<Season> seasons;
    private boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RealmList<Rating> getRating() {
        return rating;
    }

    public void setRating(RealmList<Rating> rating) {
        this.rating = rating;
    }

    public RealmList<TranslateTeam> getTranslateTeam() {
        return translateTeam;
    }

    public void setTranslateTeam(RealmList<TranslateTeam> translateTeam) {
        this.translateTeam = translateTeam;
    }

    public RealmList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(RealmList<Season> seasons) {
        this.seasons = seasons;
    }
}
