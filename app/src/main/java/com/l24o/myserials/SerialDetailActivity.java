package com.l24o.myserials;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.l24o.myserials.models.Season;
import com.l24o.myserials.models.Serial;
import com.l24o.myserials.realm.RealmHelper;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class SerialDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_detail);
        Serial serial = RealmHelper.getByCode(Realm.getInstance(getApplicationContext()), Serial.class, getIntent().getStringExtra(SeriesPageAdapter.ARG_ITEM_ID));
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Info"));
        for (Season season : serial.getSeasons()) {
            tabs.addTab(tabs.newTab().setText("Season #" + String.valueOf(serial.getSeasons().size() - (serial.getSeasons().indexOf(season)))));
        }
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        ImageView iv = (ImageView)findViewById(R.id.backdrop);
        Picasso.with(getApplicationContext())
                .load(serial.getUrl())
                .error(R.drawable.ic_broken_image_black_48dp)
                .into(iv);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SeriesPageAdapter adapter = new SeriesPageAdapter(getSupportFragmentManager(), getIntent().getStringExtra(SeriesPageAdapter.ARG_ITEM_ID), serial.getSeasons().size() + 1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

 /*       if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(SeriesPageAdapter.ARG_ITEM_ID,
                    );
            SerialDetailFragment fragment = new SerialDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.serial_detail_container, fragment)
                    .commit();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, SerialListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
