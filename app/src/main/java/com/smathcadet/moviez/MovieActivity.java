package com.smathcadet.moviez;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.smathcadet.moviez.controlers.MovieAdapter;
import com.smathcadet.moviez.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private ArrayList<Movie> mMovies;
    private MovieAdapter mMovieAdapter;
    @BindView(R.id.lvMovies) ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            mMovies = (ArrayList<Movie>) savedInstanceState.getSerializable("movies");
        } else {
            mMovies = new ArrayList<>();
        }
        mMovieAdapter = new MovieAdapter(this, mMovies);
        lvMovies.setAdapter(mMovieAdapter);

        setupRequest();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movies", mMovies);
    }

    private void setupRequest() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=0087729e5ec08e161686015665071871";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSonResult = null;

                try {
                    movieJSonResult = response.getJSONArray("results");
                    mMovies.addAll(Movie.fromJSONMovies(movieJSonResult));
                    mMovieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", mMovies.toString());
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
}
