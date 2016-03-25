package com.l24o.myserials;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l24o.myserials.models.Episode;
import com.l24o.myserials.models.TranslateTeam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MySeasonRecyclerViewAdapter extends RecyclerView.Adapter<MySeasonRecyclerViewAdapter.VHolder> {

    private final List<Episode> mValues;

    public MySeasonRecyclerViewAdapter(List<Episode> items) {
        mValues = items;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        holder.tvEpisodeCode.setText(mValues.get(position).getCode());
        holder.tvEpisodeName.setText(mValues.get(position).getName());
        holder.tvEpisodeDate.setText(new SimpleDateFormat("dd MMM yyyy", new Locale("ru", "RU")).format(mValues.get(position).getDate()));
        String teamS = "";
        for (TranslateTeam team : mValues.get(position).getTranslateTeams()) {
            teamS += team.getName() + "; ";
        }
        holder.tvEpisodeTranslatedTeams.setText(teamS);
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_season_child, parent, false);
        return new VHolder(view);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class VHolder extends RecyclerView.ViewHolder {
        public final TextView tvEpisodeCode;
        public final TextView tvEpisodeDate;
        public final TextView tvEpisodeName;
        public final TextView tvEpisodeTranslatedTeams;

        public VHolder(View view) {
            super(view);
            tvEpisodeCode = (TextView) view.findViewById(R.id.tvEpisodeCode);
            tvEpisodeDate = (TextView) view.findViewById(R.id.tvEpisodeDate);
            tvEpisodeName = (TextView) view.findViewById(R.id.tvEpisodeName);
            tvEpisodeTranslatedTeams = (TextView) view.findViewById(R.id.tvEpisodeTranslatedTeams);
        }

    }
}
