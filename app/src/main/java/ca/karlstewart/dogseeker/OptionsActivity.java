/*
    Handles UI functionality for the options menu dialog OptionsActivity
*/

package ca.karlstewart.dogseeker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import ca.karlstewart.dogseeker.model.GameOptions;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_options);

        showCurrentOptions();

    }

    private void showCurrentOptions() {
        GameOptions currOptions = GameOptions.getInstance();
        switch (currOptions.getBoardWidth()) {
            case 4:
                ((RadioButton) findViewById(R.id.optionsSize1)).setChecked(true);
                break;
            case 5:
                ((RadioButton) findViewById(R.id.optionsSize2)).setChecked(true);
                break;
            case 6:
                ((RadioButton) findViewById(R.id.optionsSize3)).setChecked(true);
                break;
            default:
                Log.w("OptionsActivity", "Unknown board size in GameOptions");
                break;
        }
        switch (currOptions.getNumDogs()) {
            case 6:
                ((RadioButton) findViewById(R.id.optionsDog6)).setChecked(true);
                break;
            case 10:
                ((RadioButton) findViewById(R.id.optionsDog6)).setChecked(true);
            case 15:
                ((RadioButton) findViewById(R.id.optionsDog6)).setChecked(true);
            case 20:
                ((RadioButton) findViewById(R.id.optionsDog6)).setChecked(true);
            default:
                Log.w("OptionsActivity", "Unknown board size in GameOptions");
                break;
        }
    }

    public static Intent makeIntent(Context c) {
        return new Intent(c, OptionsActivity.class);
    }
}