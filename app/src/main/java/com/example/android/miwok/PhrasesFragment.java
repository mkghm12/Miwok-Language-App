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
public class PhrasesFragment extends Fragment {


    public PhrasesFragment() {
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
        final ArrayList<Word> phrase = new ArrayList<Word>();
        phrase.add(new Word("minto wuksus", "Where are you going?",R.raw.phrase_where_are_you_going));
        phrase.add(new Word("tinnә oyaase'nә", "What is your name?",R.raw.phrase_what_is_your_name));
        phrase.add(new Word("oyaaset...", "My name is...",R.raw.phrase_my_name_is));
        phrase.add(new Word("michәksәs?", "How are you feeling?",R.raw.phrase_how_are_you_feeling));
        phrase.add(new Word("kuchi achit", "I’m feeling good.",R.raw.phrase_im_feeling_good));
        phrase.add(new Word("әәnәs'aa?", "Are you coming?",R.raw.phrase_are_you_coming));
        phrase.add(new Word("hәә’ әәnәm", "Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        phrase.add(new Word("әәnәm", "I’m coming.",R.raw.phrase_im_coming));
        phrase.add(new Word("yoowutis", "Let’s go.",R.raw.phrase_lets_go));
        phrase.add(new Word("әnni'nem", "Come here.",R.raw.phrase_come_here));

        WordAdapter phraseAdapter = new WordAdapter(getActivity(), phrase,R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(phraseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item = phrase.get(position);
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
