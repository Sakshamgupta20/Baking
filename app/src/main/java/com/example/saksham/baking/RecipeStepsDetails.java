package com.example.saksham.baking;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saksham.baking.steps.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeStepsDetails extends Fragment implements ExoPlayer.EventListener {

    public RecipeStepsDetails() {
    }

    private static final String TAG = RecipeStepsDetails.class.getSimpleName();

    private static final String POSITIONKEY = "KEY";
    private static final String POSITIONVIDEO = "KEYV";
    @BindView(R.id.extra)
    TextView view1;
    @BindView(R.id.next)
    Button buttonnext;
    @BindView(R.id.previous)
    Button buttonpre;
    int pos = 9999;
    long playerlong;
    long playerposition = C.TIME_UNSET;
    String value;
    List<Steps> steps;
    Steps stp;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    String videourl;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView mImage;
    private String imaget;
    private Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_recipie_steps_details, container, false);


        unbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            String posS = savedInstanceState.getString(POSITIONKEY);
            pos = Integer.parseInt(posS);
            playerposition = savedInstanceState.getLong(POSITIONVIDEO, C.TIME_UNSET);
        }
        else
            {
            pos = Recipe.getPos();
        }

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        mImage = (ImageView) rootView.findViewById(R.id.playerimage);

        steps = Recipe.getList1();


        stp = steps.get(pos);
        value = stp.getDescription();

        view1.setText(value);

        videourl = stp.getVideoURL();
        imaget = stp.getThumbnailURL();
        initializeMediaSession();

        int flag = Recipe.getF();
        if (flag == 1) {
            buttonnext.setVisibility(View.GONE);
            buttonpre.setVisibility(View.GONE);
        }

        if (videourl.equalsIgnoreCase("")) {
            Uri uri = Uri.parse(videourl);
            initializePlayer(uri);
            mPlayerView.setVisibility(View.INVISIBLE);
            if (imaget.equalsIgnoreCase("")) {
                Picasso.get().load(R.drawable.novideo).into(mImage);
                mImage.setVisibility(View.VISIBLE);
            } else {
                Picasso.get().load(imaget).into(mImage);
                mImage.setVisibility(View.VISIBLE);
            }
        } else {
            mPlayerView.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.INVISIBLE);
            Uri uri = Uri.parse(videourl);
            initializePlayer(uri);
        }

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                     playerposition = C.TIME_UNSET;
                    pos = pos + 1;
                    stp = steps.get(pos);
                    value = stp.getDescription();
                    imaget = stp.getThumbnailURL();
                    videourl = stp.getVideoURL();
                    releasePlayer();

                    if (videourl.equalsIgnoreCase("")) {
                        Uri uri = Uri.parse(videourl);
                        initializePlayer(uri);
                        mPlayerView.setVisibility(View.INVISIBLE);

                        if (imaget.equalsIgnoreCase("")) {
                            Picasso.get().load(R.drawable.novideo).into(mImage);
                            mImage.setVisibility(View.VISIBLE);
                        } else {
                            Picasso.get().load(imaget).into(mImage);
                            mImage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mPlayerView.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.INVISIBLE);
                        Uri uri = Uri.parse(videourl);
                        initializePlayer(uri);
                    }

                    buttonpre.setEnabled(true);
                    view1.setText(value);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    pos = pos - 1;
                    buttonnext.setEnabled(false);
                }
            }
        });
        buttonpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos > 0) {
                    playerposition = C.TIME_UNSET;
                    pos = pos - 1;
                    stp = steps.get(pos);
                    value = stp.getDescription();
                    imaget = stp.getThumbnailURL();
                    videourl = stp.getVideoURL();
                    releasePlayer();
                    if (videourl.equalsIgnoreCase("")) {
                        Uri uri = Uri.parse(videourl);
                        initializePlayer(uri);
                        mPlayerView.setVisibility(View.INVISIBLE);
                        if (imaget.equalsIgnoreCase("")) {
                            Picasso.get().load(R.drawable.novideo).into(mImage);
                            mImage.setVisibility(View.VISIBLE);
                        } else {
                            Picasso.get().load(imaget).into(mImage);
                            mImage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mPlayerView.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.INVISIBLE);
                        Uri uri = Uri.parse(videourl);
                        initializePlayer(uri);
                    }

                    view1.setText(value);
                    buttonnext.setEnabled(true);
                }
                if (pos == 0) {
                    buttonpre.setEnabled(false);
                }

            }
        });

        return rootView;
    }


    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            if (playerposition != C.TIME_UNSET) mExoPlayer.seekTo(playerposition);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaSession.setActive(false);
            releasePlayer();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
            playerlong = mExoPlayer.getCurrentPosition();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.getPlaybackState();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(POSITIONKEY, String.valueOf(pos));
        savedInstanceState.putLong(POSITIONVIDEO, playerlong);
    }

}