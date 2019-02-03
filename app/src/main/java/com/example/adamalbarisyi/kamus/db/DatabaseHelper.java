package com.example.adamalbarisyi.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.ENGLISHINDONESIA_TABLE;
import static com.example.adamalbarisyi.kamus.db.IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
import static com.example.adamalbarisyi.kamus.db.IndonesiaDatabaseContract.IndonesiaEnglishColumns.KATA;
import static com.example.adamalbarisyi.kamus.db.IndonesiaDatabaseContract.IndonesiaEnglishColumns.TERJEMAHAN;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.EnglishIndonesiaColumns.WORD;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.EnglishIndonesiaColumns.TRANSLATION;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENGLISHINDONESIA =
            "create table " + ENGLISHINDONESIA_TABLE +
                    " (" + _ID + " integer primary key autoincrement, " +
                    WORD + " text not null, " +
                    TRANSLATION + " text not null);";

    public static String CREATE_TABLE_INDONESIAENGLISH =
            "create table " + INDONESIAENGLISH_TABLE +
                    " (" + _ID + " integer primary key autoincrement, " +
                    KATA + " text not null, " +
                    TERJEMAHAN + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISHINDONESIA);
        db.execSQL(CREATE_TABLE_INDONESIAENGLISH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENGLISHINDONESIA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INDONESIAENGLISH_TABLE);
        onCreate(db);

    }
}

