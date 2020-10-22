/*
    Handles UI functionality for the main menu screen MainActivity
*/

package ca.karlstewart.dogseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ca.karlstewart.dogseeker.model.GameOptions;

public class MenuActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "ca.karlstewart.dogseeker - sharedprefs";
    private static final String GAME_OPTIONS_JSON = "ca.karlstewart.dogseeker - gameoptionsjson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        loadOptions();
        setupButtons();
    }

    @Override
    protected void onStop() {
        saveOptions();
        super.onStop();
    }

    // Retrieve JSON serialization from GAME_OPTIONS_JSON shared preference to init GameOptions
    private void loadOptions() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String storedGameOptionsJson = prefs.getString(GAME_OPTIONS_JSON, "");
        GameOptions.getInstance(storedGameOptionsJson);
    }
    // Store JSON serialization of current GameOptions in GAME_OPTIONS_JSON shared preference
    private void saveOptions() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String currentGameOptionsJson = GameOptions.getInstance().toJson();
        prefs.edit().putString(GAME_OPTIONS_JSON, currentGameOptionsJson).apply();
    }

    // Add listeners to navigation buttons to go to appropriate activity
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
        return new Intent(c, MenuActivity.class);
    }
}