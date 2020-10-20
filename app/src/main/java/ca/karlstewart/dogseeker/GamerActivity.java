/*
    Handles UI functionality for the main gamer activity GamerActivity
*/

package ca.karlstewart.dogseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GamerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamer);
    }

    public static Intent makeIntent(Context c) {
        Intent gamerIntent = new Intent(c, MenuActivity.class);
        return gamerIntent;
    }
}