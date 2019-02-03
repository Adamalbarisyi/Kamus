package com.example.adamalbarisyi.kamus.db;

import android.provider.BaseColumns;

public class IndonesiaDatabaseContract {

    static String INDONESIAENGLISH_TABLE = "table_indonesia_english";

    static final class IndonesiaEnglishColumns implements BaseColumns {
        static String KATA = "kata";
        static String TERJEMAHAN = "terjemahan";
    }
}
