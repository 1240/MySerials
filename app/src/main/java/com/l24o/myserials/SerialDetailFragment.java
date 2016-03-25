package com.l24o.myserials;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l24o.myserials.models.Serial;
import com.l24o.myserials.realm.RealmHelper;

import io.realm.Realm;

public class SerialDetailFragment extends Fragment {


    private Serial mItem;

    public SerialDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(SeriesPageAdapter.ARG_ITEM_ID)) {
            mItem = RealmHelper.getByCode(Realm.getInstance(getContext()), Serial.class, getArguments().getString(SeriesPageAdapter.ARG_ITEM_ID));

            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.serial_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.tvInfo)).setText(mItem.getInfo());
            ((TextView) rootView.findViewById(R.id.tvChanel)).setText(mItem.getChanel());
            ((TextView) rootView.findViewById(R.id.tvStatus)).setText(mItem.getStatus());
            ((TextView) rootView.findViewById(R.id.tvType)).setText(mItem.getGenre());
            ((TextView) rootView.findViewById(R.id.tvStartDate)).setText(mItem.getStartDate());
        }


        return rootView;
    }
}
