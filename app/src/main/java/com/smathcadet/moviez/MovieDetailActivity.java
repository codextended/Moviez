package com.smathcadet.moviez;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smathcadet.moviez.models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.tvDetailTitle) TextView tvTitle;
    @BindView(R.id.tvDetailOverview) TextView tvOverview;
    @BindView(R.id.tvDetailDate) TextView tvReleaseDate;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.ivCollapsing) ImageView ivCollapsing;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        tvTitle = findViewById(R.id.tvDetailTitle);
//        tvOverview = findViewById(R.id.tvDetailOverview);
//        tvReleaseDate = findViewById(R.id.tvDetailDate);
//        ratingBar = findViewById(R.id.ratingBar);
//        ivCollapsing = findViewById(R.id.ivCollapsing);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(String.format("Release date: %s", movie.getReleaseDate()));
        ratingBar.setRating((float) (movie.getVoteAverage()/2));
        Picasso.with(this).load(movie.getBackdropPath()).fit().centerInside()
                .placeholder(R.drawable.loading).error(R.drawable.warning).into(ivCollapsing);


        requestMovieTrailerKey();

    }

    private void requestMovieTrailerKey() {
        String trailerURL = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                String.valueOf(movie.getId()));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(trailerURL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSonResult = null;

                try {
                    movieJSonResult = response.getJSONArray("results");
                    JSONObject movieJSONObject = movieJSonResult.getJSONObject(0);
                    String trailerKey = (String) movieJSONObject.get("key");
                    Toast.makeText(MovieDetailActivity.this, trailerKey, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        return intent;
    }
}
