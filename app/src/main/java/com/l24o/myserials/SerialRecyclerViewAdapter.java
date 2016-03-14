package com.l24o.myserials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.l24o.myserials.models.Serial;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chuff on 14.03.2016.
 */
public class SerialRecyclerViewAdapter extends RecyclerView.Adapter<SerialRecyclerViewAdapter.ViewHolder> {

    private final List<Serial> mValues;
    private final boolean mTwoPane;
    private final AppCompatActivity activity;

    public SerialRecyclerViewAdapter(List<Serial> items, boolean mTwoPane, AppCompatActivity context) {
        mValues = items;
        this.mTwoPane = mTwoPane;
        activity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serial_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Serial serial = mValues.get(position);
        holder.mItem = serial;
        holder.mName.setText(serial.getName());
        Picasso.with(activity)
                .load(serial.getUrl())
                .error(R.drawable.ic_broken_image_black_48dp)
                .into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(SerialDetailFragment.ARG_ITEM_ID, holder.mItem.getCode());
                    SerialDetailFragment fragment = new SerialDetailFragment();
                    fragment.setArguments(arguments);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.serial_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SerialDetailActivity.class);
                    intent.putExtra(SerialDetailFragment.ARG_ITEM_ID, holder.mItem.getCode());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swap(ArrayList<Serial> datas) {
        mValues.clear();
        mValues.addAll(datas);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final ImageView mImage;
        public Serial mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.name);
            mImage = (ImageView) view.findViewById(R.id.image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
