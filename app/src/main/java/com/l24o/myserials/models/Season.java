package com.l24o.myserials.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by chuff on 14.03.2016.
 */
public class Season extends RealmObject {

    private int number;
    private RealmList<Episode> episodes;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RealmList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(RealmList<Episode> episodes) {
        this.episodes = episodes;
    }
}
