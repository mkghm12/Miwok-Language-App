package com.example.android.miwok;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.AUDIO_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {


    public FamilyFragment() {
        // Required empty public constructor
    }
    private MediaPlayer mediaPlayer;
    private AudioManager am;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMedia();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mcompleteListener =new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMedia();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        am = (AudioManager)getActivity().getSystemService(AUDIO_SERVICE);
        final ArrayList<Word> relation = new ArrayList<Word>();
        relation.add(new Word("әpә","father",R.drawable.family_father,R.raw.family_father));
        relation.add(new Word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        relation.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        relation.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        relation.add(new Word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        relation.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        relation.add(new Word("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        relation.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        relation.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        relation.add(new Word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter familyAdapter  = new WordAdapter(getActivity(), relation,R.color.category_family);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(familyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item = relation.get(position);
                releaseMedia();
                int result = am.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(),item.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mcompleteListener);
                }

            }
        });
        return rootView;
    }
    private void releaseMedia(){
        if(mediaPlayer !=null){
            mediaPlayer.release();
            mediaPlayer=null;
            am.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMedia();
    }
}
