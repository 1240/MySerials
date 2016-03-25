package com.l24o.myserials;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.l24o.myserials.models.Season;
import com.l24o.myserials.models.Serial;
import com.l24o.myserials.realm.RealmHelper;

import io.realm.Realm;

public class SeasonFragment extends Fragment {

    private Season mItem;

    public SeasonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(SeriesPageAdapter.ARG_ITEM_ID)) {

            int pos = getArguments().getInt(SeriesPageAdapter.ARG_POS);
            Serial serial = RealmHelper.getByCode(Realm.getInstance(getContext()), Serial.class, getArguments().getString(SeriesPageAdapter.ARG_ITEM_ID));
            mItem = serial.getSeasons().get(pos);
            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(serial.getName());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season_list, container, false);

        if (mItem != null) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MySeasonRecyclerViewAdapter adapter = new MySeasonRecyclerViewAdapter(mItem.getEpisodes());
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

}
