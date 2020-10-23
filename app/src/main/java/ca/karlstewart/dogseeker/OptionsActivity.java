/*
    Handles UI functionality for the options menu dialog OptionsActivity
*/

package ca.karlstewart.dogseeker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ca.karlstewart.dogseeker.model.GameOptions;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_options);

        showCurrentOptions();
        setupOptionButtons();
        setupResetButtons();
    }

    // Updates options UI with current settings in GameOptions
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

    private void setupOptionButtons() {
        // Setup Size Options
        ((RadioButton) findViewById(R.id.optionsSize1)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setBoardSize(4,6);
            shortToast(R.string.options_toast_size_updated);
        });
        ((RadioButton) findViewById(R.id.optionsSize2)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setBoardSize(5,10);
            shortToast(R.string.options_toast_size_updated);
        });
        ((RadioButton) findViewById(R.id.optionsSize3)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setBoardSize(6,15);
            shortToast(R.string.options_toast_size_updated);
        });

        // Setup Dog Count Options
        ((RadioButton) findViewById(R.id.optionsDog6)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setNumDogs(6);
            shortToast(R.string.options_toast_dog_count_updated);
        });
        ((RadioButton) findViewById(R.id.optionsDog10)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setNumDogs(10);
            shortToast(R.string.options_toast_dog_count_updated);
        });
        ((RadioButton) findViewById(R.id.optionsDog15)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setNumDogs(15);
            shortToast(R.string.options_toast_dog_count_updated);
        });
        ((RadioButton) findViewById(R.id.optionsDog20)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().setNumDogs(20);
            shortToast(R.string.options_toast_dog_count_updated);
        });
    }

    private void shortToast(int txtID) {
        Toast toast = new Toast(OptionsActivity.this);
        toast.setText(txtID);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    // Add onClickListeners to buttons which will reset the appropriate var in GameOptions
    private void setupResetButtons() {
        ((Button) findViewById(R.id.optionsButtonResetPlays)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().resetTotalPlays();
            Toast toast = new Toast(OptionsActivity.this);
            toast.setText(R.string.options_toast_reset_plays);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        });
        ((Button) findViewById(R.id.optionsButtonResetScores)).setOnClickListener( (View v) -> {
            GameOptions.getInstance().resetScores();
            Toast toast = new Toast(OptionsActivity.this);
            toast.setText(R.string.options_toast_reset_scores);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    // Basic intent factory for OptionsActivity
    public static Intent makeIntent(Context c) {
        return new Intent(c, OptionsActivity.class);
    }
}