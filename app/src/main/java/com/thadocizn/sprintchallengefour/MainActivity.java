package com.thadocizn.sprintchallengefour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thadocizn.sprintchallengefour.apiaccess.MovieApiDao;
import com.thadocizn.sprintchallengefour.apiaccess.MovieOverview;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText keySearch;
    private LinearLayout parent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        FavoriteMovieDbDao.initializeInstance(context);
        keySearch = findViewById(R.id.etKeywordSearch);
        parent = findViewById(R.id.parentLayout);

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ArrayList<MovieOverview> overviews = MovieApiDao.searchMovies(keySearch.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                for (int i = 0; i < overviews.size(); i++) {
                                    final String title = overviews.get(i).getTitle();
                                    final String overView = overviews.get(i).getOverview();
                                    final String release = overviews.get(i).getRelease_date();
                                    final Double votes = overviews.get(i).getVote_average();


                                    TextView textView = new TextView(context);
                                    textView.setText(title );
                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FavoriteMovie movie = new FavoriteMovie(title, overView,release,votes );
                                            FavoriteMovieDbDao.createFavoriteMovie(movie);
                                        }
                                    });
                                    parent.addView(textView);
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        findViewById(R.id.btnFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FavoriteMoviesActivity.class);
                startActivity(intent);
            }
        });
    }
    }

