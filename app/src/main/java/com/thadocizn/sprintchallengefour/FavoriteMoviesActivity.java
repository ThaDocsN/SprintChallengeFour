package com.thadocizn.sprintchallengefour;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteMoviesActivity extends AppCompatActivity {

    LinearLayout favorites;
    ArrayList<FavoriteMovie> favoriteMovies;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        context = this;

        favorites = findViewById(R.id.favoriteParentLayout);
        favoriteMovies = FavoriteMovieDbDao.readFavorites();

        favoriteMovies = new ArrayList<>();
        favoriteMovies = FavoriteMovieDbDao.readFavorites();

        for (int i = 0; i < favoriteMovies.size(); i++) {
            TextView favMovies = new TextView(context);
            FavoriteMovie movie = favoriteMovies.get(i);
            favMovies.setText(String.format("%s\n%s\n%s\n%s", movie.getTitle(), movie.getOverview(), movie.getRelease_date(), movie.getVote_average()));
            favorites.addView(favMovies);
        }


    }
}