/*
    Handles UI functionality for the main menu screen MainActivity
*/

package ca.karlstewart.dogseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupButtons();
    }

    private void setupButtons() {
        Button playButton = (Button) findViewById(R.id.menuButtonPlay);
    }

    public static Intent makeIntent(Context c) {
        Intent menuIntent = new Intent(c, MenuActivity.class);
        return menuIntent;
    }
}