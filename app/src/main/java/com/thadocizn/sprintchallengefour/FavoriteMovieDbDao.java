package com.thadocizn.sprintchallengefour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class FavoriteMovieDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        if (db == null) {
            FavoritesDbHelper helper = new FavoritesDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static void createFavoriteMovie(FavoriteMovie movie) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE, movie.getTitle());
            values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
            values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_RELEASE_DATE, movie.getRelease_date());
            values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVote_average());

            long resultId = db.insert(FavoriteMoviesDbContract.FavoritesEntry.TABLE_NAME, null, values);

        }
    }

    public static ArrayList<FavoriteMovie> readFavorites() {

        int index;
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(String.format("SELECT * FROM %s;",
                    FavoriteMoviesDbContract.FavoritesEntry.TABLE_NAME), null);

            ArrayList<FavoriteMovie> favoriteMovies = new ArrayList<>();

            if (cursor.moveToNext()) {
                FavoriteMovie favoriteMovie;
                index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE);
                String title = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_OVERVIEW);
                String overView = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_RELEASE_DATE);
                String releaseDate = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE);
                Double voteAverage = Double.valueOf(cursor.getString(index));

                favoriteMovie = new FavoriteMovie(title, overView, releaseDate, voteAverage);
                favoriteMovies.add(favoriteMovie);
            } else {
                favoriteMovies = null;
            }
            cursor.close();
            return favoriteMovies;

        } else {
            return null;
        }
    }

    public static void updateNote(FavoriteMovie movie) {
        if (db != null) {
            String whereClause = String.format("%s = '%s'",
                    FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE,
                    movie.getTitle());

            final Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                    FavoriteMoviesDbContract.FavoritesEntry.TABLE_NAME,
                    whereClause),
                    null);

            if(cursor.getCount() == 1) {
                ContentValues values = new ContentValues();
                values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE, movie.getTitle());
                values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
                values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_RELEASE_DATE, movie.getRelease_date());
                values.put(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVote_average());

                final int affectedRows = db.update(FavoriteMoviesDbContract.FavoritesEntry.TABLE_NAME, values, whereClause, null);
            }
        }
    }

    public static void deleteNote(FavoriteMovie movie) {
        if (db != null) {
            String whereClause = String.format("%s = '%s'",
                    FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE,
                    movie.getTitle());

            int affectedRows = db .delete(FavoriteMoviesDbContract.FavoritesEntry.TABLE_NAME, whereClause, null);
        }
    }

    @NonNull
    private static FavoriteMovie getFavoriteMovieFromCursor(Cursor cursor) {
        int index;
        FavoriteMovie favoriteMovie;
        index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_TITLE);
        String title = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_OVERVIEW);
        String overView = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_RELEASE_DATE);
        String releaseDate = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(FavoriteMoviesDbContract.FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE);
        Double voteAverage = Double.valueOf(cursor.getString(index));

        favoriteMovie = new FavoriteMovie(title, overView, releaseDate, voteAverage);

        return favoriteMovie;
    }
}
