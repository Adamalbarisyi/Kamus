package com.example.adamalbarisyi.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.example.adamalbarisyi.kamus.model.DictionaryModel;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.ENGLISHINDONESIA_TABLE;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.EnglishIndonesiaColumns.TRANSLATION;
import static com.example.adamalbarisyi.kamus.db.EnglishDatabaseContract.EnglishIndonesiaColumns.WORD;
import static com.example.adamalbarisyi.kamus.db.IndonesiaDatabaseContract.IndonesiaEnglishColumns.KATA;
import static com.example.adamalbarisyi.kamus.db.IndonesiaDatabaseContract.IndonesiaEnglishColumns.TERJEMAHAN;

public class DictionaryHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<DictionaryModel> getDataByName(String nama) {
        String results = "";
        Cursor cursor =
                database.query(ENGLISHINDONESIA_TABLE, null, WORD + " LIKE ?", new String[]{nama}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                dictionaryModel.setTranslation(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION)));
                arrayList.add(dictionaryModel);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor searchQueryByName(String search,  boolean isEnglish) {
        String TABLE_NAME = isEnglish ? EnglishDatabaseContract.ENGLISHINDONESIA_TABLE : IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
        String WORDS = isEnglish? WORD : KATA;
        return database.rawQuery("SELECT * FROM "+
                TABLE_NAME+" WHERE "+ WORDS +
                " LIKE '%"+search.trim()+"%'", null);
    }

    public final ArrayList<DictionaryModel> getData(String search,  boolean isEnglish) {
        Cursor cursor = searchQueryByName(search, isEnglish);
        ArrayList<DictionaryModel> result = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            for (int i = 0; i<cursor.getCount();i++){
                result.add(new DictionaryModel(cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }


    public long insert(DictionaryModel dictionaryModel, boolean isEnglish) {
        String TABLE_NAME = isEnglish ? EnglishDatabaseContract.ENGLISHINDONESIA_TABLE : IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
        String WORDS = isEnglish ? WORD : KATA;
        String TRANSLATE = isEnglish ? TRANSLATION : TERJEMAHAN;
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORDS, dictionaryModel.getWord());
        initialValues.put(TRANSLATE, dictionaryModel.getTranslation());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSucces() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(DictionaryModel dictionaryModel, boolean isEnglish) {
        String TABLE_NAME = isEnglish ? EnglishDatabaseContract.ENGLISHINDONESIA_TABLE : IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
        String WORDS = isEnglish ? WORD : KATA;
        String TRANSLATE = isEnglish ? TRANSLATION : TERJEMAHAN;
        String sql = "INSERT INTO " + TABLE_NAME + " (" + WORDS + ", " + TRANSLATE
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, dictionaryModel.getWord());
        stmt.bindString(2, dictionaryModel.getTranslation());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(DictionaryModel dictionaryModel, boolean isEnglish) {
        ContentValues args = new ContentValues();
        String TABLE_NAME = isEnglish ? EnglishDatabaseContract.ENGLISHINDONESIA_TABLE
                : IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
        String WORDS = isEnglish ? WORD : KATA;
        String TRANSLATE = isEnglish ? TRANSLATION : TERJEMAHAN;

        args.put(WORDS, dictionaryModel.getWord());
        args.put(TRANSLATE, dictionaryModel.getTranslation());
        return database.update(TABLE_NAME, args, _ID + "= '" + dictionaryModel.getId() + "'", null);
    }


    public int delete(int id, boolean isEnglish) {
        String TABLE_NAME = isEnglish ? EnglishDatabaseContract.ENGLISHINDONESIA_TABLE : IndonesiaDatabaseContract.INDONESIAENGLISH_TABLE;
        return database.delete(TABLE_NAME, _ID + "= '" + id + "'", null);
    }


}
