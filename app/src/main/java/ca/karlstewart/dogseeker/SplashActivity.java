/*
    Handles UI functionality for the splash screen SplashActivity
*/

package ca.karlstewart.dogseeker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    MediaPlayer audioIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        View topView = findViewById(R.id.splashTopView);
        topView.setOnClickListener((View v) -> { goToMenuActivity(); });
        VideoView vid = setupSplashVideo();
        MediaPlayer audio = setupSplashAudio();

        vid.setOnPreparedListener( (MediaPlayer mp) -> {
            vid.start();
            handler.postDelayed(() -> {
                vid.setAlpha(1);
                audio.start();
            },250);
        });


    }

    private VideoView setupSplashVideo() {
        VideoView splashVideo = (VideoView) findViewById(R.id.splashVideo);
        splashVideo.setOnCompletionListener( (MediaPlayer mp) -> {
            goToMenuActivity();
        });
        splashVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_splash));
        return splashVideo;
    }

    private MediaPlayer setupSplashAudio() {
        audioIntro = MediaPlayer.create(SplashActivity.this, R.raw.music_splash_intro);
        return audioIntro;
    }

    private void goToMenuActivity(){
        Intent menuIntent = MenuActivity.makeIntent(SplashActivity.this);
        startActivity(menuIntent);
        finish();
    }
}