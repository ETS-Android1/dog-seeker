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
    MediaPlayer splashAudio;
    VideoView splashVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View topView = findViewById(R.id.splashTopView);
        topView.setOnClickListener((View v) -> { goToMenuActivity(); });

        setupSplashVideo().start();
        setupSplashAudio();
    }

    @Override
    protected void onResume() {
        // Unhide splashVideo after giving it 300 ms to load
        splashVideo.setOnPreparedListener( (MediaPlayer mp) -> {
            splashAudio.start();
            new Handler().postDelayed( () -> {splashVideo.setAlpha(1);}, 300);
        });

        super.onResume();
    }

    private VideoView setupSplashVideo() {
        splashVideo = (VideoView) findViewById(R.id.splashVideo);
        splashVideo.setOnCompletionListener( (MediaPlayer mp) -> {
            // Remove tap to skip option while already moving to main menu
            findViewById(R.id.splashTopView).setOnClickListener(null);
            goToMenuActivity();
        });
        splashVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_splash));
        return splashVideo;
    }

    private MediaPlayer setupSplashAudio() {
        splashAudio = MediaPlayer.create(SplashActivity.this, R.raw.music_splash_intro);
        return splashAudio;
    }

    private void goToMenuActivity(){
        Intent menuIntent = MenuActivity.makeIntent(SplashActivity.this);
        startActivity(menuIntent);
        finish();
    }
}