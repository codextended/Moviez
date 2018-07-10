package com.smathcadet.moviez.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Smath Cadet on 7/6/2018.
 */

public class Movie implements Serializable{
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String backdropPath;
    private String releaseDate;
    private Double voteAverage;
    private int id;

    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.releaseDate = jsonObject.getString("release_date");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.id = jsonObject.getInt("id");
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Movie> fromJSONMovies(JSONArray jsonArray){
        ArrayList<Movie> result = new ArrayList<>();
        for (int x = 0; x < jsonArray.length(); x++){
            try {
                result.add(new Movie(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
