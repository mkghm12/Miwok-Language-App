package com.example.android.miwok;

/**
 * Created by manish on 3/3/18.
 */

public class Word {
    private String english_word;
    private String miwok_word;
    private int imageResourceId=isImage;
    private static final int isImage =-1;
    private int audioResourceId;
    public Word(){
        this.english_word="";
        this.miwok_word="";
    }
    public Word(String miwok,String eng,int audioResourceId){
        this.english_word=eng;
        this.miwok_word=miwok;
        this.audioResourceId=audioResourceId;
    }
    public Word(String miwok,String eng,int id,int audioResourceId){
        this.english_word=eng;
        this.miwok_word=miwok;
        this.imageResourceId=id;
        this.audioResourceId=audioResourceId;
    }
    public String getMiwok_word(){
        return  miwok_word;
    }
    public String getEnglish_word(){
        return this.english_word;
    }
    public int getImageResourceId(){
        return this.imageResourceId;
    }
    public int getAudioResourceId(){
        return this.audioResourceId;
    }
    public boolean hasImage(){
        if(imageResourceId==-1){
            return false;
        }else return true;
    }


}
