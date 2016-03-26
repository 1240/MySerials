package com.l24o.myserials;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.l24o.myserials.adapters.OnLoadMoreListener;
import com.l24o.myserials.adapters.SerialRecyclerViewAdapter;
import com.l24o.myserials.models.Serial;
import com.l24o.myserials.realm.RealmHelper;
import com.l24o.myserials.retrofit.loaders.RetorfitLoader;
import com.l24o.myserials.retrofit.response.Response;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SerialListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Response> {

    protected Handler handler;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private int from = 0;
    private boolean byLike = false;
    private SerialRecyclerViewAdapter adapter;
    private List<Serial> serials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_list);
        serials = new ArrayList<>(RealmHelper.getByClass(Realm.getInstance(this), Serial.class, byLike));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (byLike) {
                    fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
                    Snackbar.make(view, "Now sort by name", Snackbar.LENGTH_LONG).show();
                } else {
                    fab.setImageResource(R.drawable.ic_favorite_white_48dp);
                    Snackbar.make(view, "Now sort by liked", Snackbar.LENGTH_LONG).show();
                }
                byLike = !byLike;
            }
        });

        View recyclerView = findViewById(R.id.serial_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.serial_detail_container) != null) {
            mTwoPane = true;
        }
        if (serials.isEmpty()) {
            serials.add(null);
            adapter.notifyItemInserted(serials.size() - 1);
            getSupportLoaderManager().initLoader(R.id.loader, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setHasFixedSize(true);

        adapter = new SerialRecyclerViewAdapter(serials, mTwoPane, this, recyclerView);

        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                from = serials.size();
                serials.add(null);
                adapter.notifyItemInserted(serials.size() - 1);
                getSupportLoaderManager().restartLoader(R.id.loader, Bundle.EMPTY, SerialListActivity.this);

            }
        });
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case R.id.loader:
                return new RetorfitLoader(this, from, "");
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        int id = loader.getId();
        if (id == R.id.loader) {
            serials.remove(serials.size() - 1);
            adapter.notifyItemRemoved(serials.size());
            List<Serial> serialList = data.getTypedAnswer();

            if (serialList != null) {
                for (Serial s : serialList) {
                    if (!contains(serials, s)) {
                        serials.add(s);
                        adapter.notifyItemInserted(serials.size());
                    }
                }
            }
            adapter.setLoaded();
        }
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {

    }

    private boolean contains(List<Serial> sList, Serial s) {
        for (Serial serial : sList) {
            if (serial.getCode().equals(s.getCode()))
                return true;
        }
        return false;
    }
}
