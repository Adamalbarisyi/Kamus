package com.example.adamalbarisyi.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import com.example.adamalbarisyi.kamus.db.DictionaryHelper;
import com.example.adamalbarisyi.kamus.model.DictionaryModel;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        DictionaryPreference dictionaryPreference;
        double progress;
        double maxprogress = 100;


        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(MainActivity.this);
            dictionaryPreference = new DictionaryPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = dictionaryPreference.getFirstRun();

            if (firstRun) {
                ArrayList<DictionaryModel> dictionaryEnglish = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> dictionaryIndonesia = preLoadRaw(R.raw.indonesia_english);
                dictionaryHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / dictionaryEnglish.size() + dictionaryIndonesia.size();
                dictionaryHelper.beginTransaction();

                try {
                    for (DictionaryModel model : dictionaryEnglish) {
                        dictionaryHelper.insertTransaction(model, true);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    for (DictionaryModel model : dictionaryIndonesia) {
                        dictionaryHelper.insertTransaction(model, false);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    dictionaryHelper.setTransactionSucces();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground : Exception");
                }

                dictionaryHelper.endTransaction();
                dictionaryHelper.close();
                dictionaryPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(int data) {
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                DictionaryModel dictionaryModel;
                dictionaryModel = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
                count++;
            }
            while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }

}
