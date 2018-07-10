package com.smathcadet.moviez.controlers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smathcadet.moviez.MovieActivity;
import com.smathcadet.moviez.MovieDetailActivity;
import com.smathcadet.moviez.R;
import com.smathcadet.moviez.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Smath Cadet on 7/7/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder{
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @BindView(R.id.ivPoster) ImageView ivPoster;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public MovieAdapter(Context context, List<Movie> movieList){
        super(context, android.R.layout.simple_list_item_1, movieList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Movie movie = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        String imagePath = null;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            imagePath = movie.getPosterPath();
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            imagePath = movie.getBackdropPath();
        }
        Picasso.with(getContext()).load(imagePath).fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.loading).error(R.drawable.warning).into(viewHolder.ivPoster);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MovieDetailActivity.newIntent(getContext(), movie);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
