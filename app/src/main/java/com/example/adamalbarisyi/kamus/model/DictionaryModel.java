package com.example.adamalbarisyi.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable {
    private int id;
    private String word;
    private String translation;

    public DictionaryModel() {

    }

    public DictionaryModel(String word, String translation){
        this.word = word;
        this.translation = translation;
    }


    public DictionaryModel(int id, String word, String translation){
        this.id = id;
        this.word = word;
        this.translation = translation;
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.translation = in.readString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public static final Parcelable.Creator<DictionaryModel> CREATOR = new Parcelable.Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.translation);
    }
}
