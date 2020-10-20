/*
    Handles UI functionality for the splash screen SplashActivity
*/

package ca.karlstewart.dogseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setupSplashVideo();
    }

    private void setupSplashVideo() {
        VideoView splashVideo = (VideoView) findViewById(R.id.splashVideo);
        splashVideo.setOnClickListener( (View v)-> {
            Intent menuIntent = MenuActivity.makeIntent(SplashActivity.this);
            startActivity(menuIntent);
        });
    }
}