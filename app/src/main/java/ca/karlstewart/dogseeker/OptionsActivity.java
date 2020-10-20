/*
    Handles UI functionality for the options menu dialog OptionsActivity
*/

package ca.karlstewart.dogseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public static Intent makeIntent(Context c) {
        Intent optionsIntent = new Intent(c, MenuActivity.class);
        return optionsIntent;
    }
}