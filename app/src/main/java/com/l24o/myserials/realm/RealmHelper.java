package com.l24o.myserials.realm;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;

/**
 * Created by chuff on 14.03.2016.
 */
public class RealmHelper {

    public static <T extends RealmObject> T getByCode(@NonNull Realm realm, Class<T> clazz, String code) {
        return realm.where(clazz).equalTo("code", code).findFirst();
    }

    public static <T extends RealmObject> List<T> getByClass(@NonNull Realm realm, Class<T> clazz, boolean sort) {
        if (sort) {
            return realm.allObjects(clazz);
        } else {
            return realm.allObjectsSorted(clazz, "checked", Sort.ASCENDING);
        }
    }

    public static <T extends RealmObject> void save(@NonNull Realm realm, List<T> data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
    }

    public static <T extends RealmObject> void save(@NonNull Realm realm, T data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
    }

    public static <T extends RealmObject> void clear(@NonNull Realm realm, Class<T> clazz) {
        realm.beginTransaction();
        realm.clear(clazz);
        realm.commitTransaction();
    }


}
