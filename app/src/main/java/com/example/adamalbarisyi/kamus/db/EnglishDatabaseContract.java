package com.example.adamalbarisyi.kamus.db;

import android.provider.BaseColumns;

public class EnglishDatabaseContract {
    static String ENGLISHINDONESIA_TABLE = "table_english_indonesia";

    static final class EnglishIndonesiaColumns implements BaseColumns {
        static String WORD = "word";
        static String TRANSLATION = "translation";
    }
}
