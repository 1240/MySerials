package com.l24o.myserials.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.l24o.myserials.R;
import com.l24o.myserials.SerialDetailActivity;
import com.l24o.myserials.SerialDetailFragment;
import com.l24o.myserials.models.Episode;
import com.l24o.myserials.models.Season;
import com.l24o.myserials.models.Serial;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;

/**
 * @author chuff on 14.03.2016.
 */
public class SerialRecyclerViewAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Serial> mValues;
    private final boolean mTwoPane;
    private final AppCompatActivity activity;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public SerialRecyclerViewAdapter(List<Serial> items, boolean mTwoPane, AppCompatActivity context, RecyclerView recyclerView) {
        mValues = items;
        this.mTwoPane = mTwoPane;
        activity = context;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if ((!loading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                            && lastVisibleItem > 0) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.serial_list_content, parent, false);

            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;

      /*  View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serial_list_content, parent, false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder vholder = (ViewHolder) holder;
            Serial serial = mValues.get(position);
            vholder.mItem = serial;
            vholder.mName.setText(serial.getName());
            vholder.mCode.setText(getLastEpisode(serial).getCode());
            vholder.mDate.setText(new SimpleDateFormat("dd MMM yyyy", new Locale("ru", "RU")).format(getLastEpisode(serial).getDate()));
            Picasso.with(activity)
                    .load(serial.getUrl())
                    .error(R.drawable.ic_broken_image_black_48dp)
                    .into(vholder.mImage);

            vholder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(SeriesPageAdapter.ARG_ITEM_ID, vholder.mItem.getCode());
                        SerialDetailFragment fragment = new SerialDetailFragment();
                        fragment.setArguments(arguments);
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.serial_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, SerialDetailActivity.class);
                        intent.putExtra(SeriesPageAdapter.ARG_ITEM_ID, vholder.mItem.getCode());
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swap(List<Serial> datas) {
        mValues.clear();
        mValues.addAll(datas);
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private Episode getLastEpisode(Serial serial) {
        Season first = serial.getSeasons().first();
        RealmList<Episode> episodes = first.getEpisodes();
        for (Episode ep : episodes) {
            if (!ep.getCode().contains("#")) {
                return ep;
            }
        }
        return episodes.last();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final ImageView mImage;
        public final TextView mCode;
        public final TextView mDate;
        public Serial mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.name);
            mDate = (TextView) view.findViewById(R.id.lastEpisodeDate);
            mCode = (TextView) view.findViewById(R.id.lastEpisodeDate);
            mImage = (ImageView) view.findViewById(R.id.image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
