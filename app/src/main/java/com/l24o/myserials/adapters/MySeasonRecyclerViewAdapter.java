package com.l24o.myserials.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l24o.myserials.R;
import com.l24o.myserials.models.Episode;
import com.l24o.myserials.models.TranslateTeam;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MySeasonRecyclerViewAdapter extends RecyclerView.Adapter<MySeasonRecyclerViewAdapter.VHolder> {

    private final List<Episode> mValues;

    public MySeasonRecyclerViewAdapter(List<Episode> items) {
        mValues = items;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        Episode episode = mValues.get(position);
        holder.tvEpisodeCode.setText(episode.getCode());
        holder.tvEpisodeName.setText(episode.getName());
        if (episode.getDate() != null)
            holder.tvEpisodeDate.setText(new SimpleDateFormat("dd MMM yyyy", new Locale("ru", "RU")).format(episode.getDate()));
        else
            holder.tvEpisodeDate.setText("---");
        String teamS = "";
        for (TranslateTeam team : episode.getTranslateTeams()) {
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
