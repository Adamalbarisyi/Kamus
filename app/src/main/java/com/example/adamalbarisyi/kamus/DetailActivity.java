package com.example.adamalbarisyi.kamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

     TextView tvWord,tvTranslation;
    public static final String WORDS = "WORDS";
    public static final String TRANSLATE = "TRANSLATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



    tvWord = findViewById(R.id.tv_Word);
    tvTranslation = findViewById(R.id.tv_translation);
    tvWord.setText(getIntent().getStringExtra(WORDS));
    tvTranslation.setText(getIntent().getStringExtra(TRANSLATE));
    }
}
