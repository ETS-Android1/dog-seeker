/*
    Handles UI functionality for the main menu screen MainActivity
*/

package ca.karlstewart.dogseeker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupButtons();
    }

    private void setupButtons() {
        Button playButton = (Button) findViewById(R.id.menuButtonPlay);
        Button optionsButton = (Button) findViewById(R.id.menuButtonOptions);
        Button aboutButton = (Button) findViewById(R.id.menuButtonAbout);
        playButton.setOnClickListener( (View v) -> {
            startActivity(GamerActivity.makeIntent(MenuActivity.this));
        });
        optionsButton.setOnClickListener( (View v) -> {
            startActivity(OptionsActivity.makeIntent(MenuActivity.this));
        });
        aboutButton.setOnClickListener( (View v) -> {
            startActivity(HelpActivity.makeIntent(MenuActivity.this));
        });

    }



    public static Intent makeIntent(Context c) {
        Intent menuIntent = new Intent(c, MenuActivity.class);
        return menuIntent;
    }
}