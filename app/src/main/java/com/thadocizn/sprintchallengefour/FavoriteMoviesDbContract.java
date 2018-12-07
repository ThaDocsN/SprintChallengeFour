package com.thadocizn.sprintchallengefour;

import android.provider.BaseColumns;

public class FavoriteMoviesDbContract {

    public static class FavoritesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_TITLE + " TEXT, " +
                COLUMN_NAME_OVERVIEW + " TEXT, " +
                COLUMN_NAME_RELEASE_DATE + " TEXT, " +
                COLUMN_NAME_VOTE_AVERAGE + " DOUBLE);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }
}
