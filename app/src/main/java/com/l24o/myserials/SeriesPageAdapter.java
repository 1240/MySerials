package com.l24o.myserials;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.l24o.myserials.models.Serial;

/**
 * Created by l24o on 25.03.16.
 */
public class SeriesPageAdapter extends FragmentPagerAdapter {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_POS = "pos";
    private final int count;
    private String itemId;

    public SeriesPageAdapter(FragmentManager fm, String itemId, int count) {
        super(fm);
        this.count = count;
        this.itemId = itemId;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_ITEM_ID, itemId);
        switch (position) {
            case 0:
                SerialDetailFragment fragment = new SerialDetailFragment();
                fragment.setArguments(arguments);
                return fragment;
            default:
                arguments.putInt(ARG_POS, position - 1);
                SeasonFragment seasonFragment = new SeasonFragment();
                seasonFragment.setArguments(arguments);
                return seasonFragment;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
