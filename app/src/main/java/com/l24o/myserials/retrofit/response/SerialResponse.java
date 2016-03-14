package com.l24o.myserials.retrofit.response;

import android.content.Context;

import com.l24o.myserials.models.Serial;
import com.l24o.myserials.realm.RealmHelper;

import io.realm.Realm;

/**
 * @author Alexander Popov created on 01.03.2016.
 */
public class SerialResponse extends Response {

    @Override
    public void save(Context context) {
        Serial serial = getTypedAnswer();
        if (serial != null) {
                RealmHelper.save(Realm.getInstance(context), serial);
        }
    }
}
