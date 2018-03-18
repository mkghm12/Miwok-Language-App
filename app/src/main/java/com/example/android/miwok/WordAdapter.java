package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manish on 3/3/18.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int colorId;
    public WordAdapter(Activity context, ArrayList<Word> words,int ColorResourceId) {
        super(context,0,words);
        colorId=ColorResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if(listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word item = getItem(position);
        TextView textView1 = (TextView)listitemView.findViewById(R.id.miwok_item);
        textView1.setText(item.getMiwok_word());
        TextView textView2 = (TextView)listitemView.findViewById(R.id.eng_item);
        textView2.setText(item.getEnglish_word());
        ImageView imgview = (ImageView)listitemView.findViewById(R.id.img);
        if(item.hasImage()){
            imgview.setImageResource(item.getImageResourceId());
            imgview.setVisibility(View.VISIBLE);
        }else {
            imgview.setVisibility(View.GONE);
        }

        View textContainer =listitemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),colorId);
        textContainer.setBackgroundColor(color);
        return listitemView;
    }
}
