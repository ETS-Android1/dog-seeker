/*
    Handles UI functionality for the main gamer activity GamerActivity
*/

package ca.karlstewart.dogseeker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import ca.karlstewart.dogseeker.model.GameBoard;
import ca.karlstewart.dogseeker.model.GameBoardTile;
import ca.karlstewart.dogseeker.model.GameOptions;

public class GamerActivity extends AppCompatActivity {
    GameBoard gameBoard;
    ArrayList<Button> scannedButtons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamer);

        // Init gameBoard using GameOptions singleton
        gameBoard = new GameBoard(GameOptions.getInstance());

        // Setup UI representation of gameBoard
        updateTextDisplays();
        setupTileButtons();

    }

    private void updateTextDisplays() {
        TextView textFoundDogs = (TextView) findViewById(R.id.gamerTextFoundDogs);
        textFoundDogs.setText( getString(R.string.gamer_text_found_dogs, gameBoard.getDogsFound()) );

        TextView textScans = (TextView) findViewById(R.id.gamerTextScans);
        textScans.setText( getString(R.string.gamer_text_scans, gameBoard.getScansUsed()) );

        TextView textPlays = (TextView) findViewById(R.id.gamerTextPlays);
        textPlays.setText( getString(R.string.gamer_text_plays, GameOptions.getInstance().getTotalPlays()) );

        TextView textHighScore = (TextView) findViewById(R.id.gamerTextHighScore);
        if (GameOptions.getInstance().getHighScore() > 0) {
            // Build string containing high scores
            String scoreText = "\n";
            int i = 1;
            for (Integer score : GameOptions.getInstance().getHighScores()) {
                scoreText = scoreText.concat(String.format(Locale.getDefault(),"%d. %d\t\t", i, score));
                i++;
            }
            textHighScore.setText(getString(R.string.gamer_text_high_scores,
                    GameOptions.getInstance().getBoardHeight(),
                    GameOptions.getInstance().getBoardWidth(),
                    GameOptions.getInstance().getNumDogs(),
                    scoreText));
        }
        else {
            textHighScore.setText(R.string.gamer_text_high_scores_none);
        }
    }

    public void setupTileButtons() {
        int height = GameOptions.getInstance().getBoardHeight();
        int width = GameOptions.getInstance().getBoardWidth();
        TableLayout buttonTable = (TableLayout) findViewById(R.id.gamerTableButtons);

        // Create rows
        for (int y = 0; y < height; y++) {
            // Construct row of buttons
            TableRow currentRow = new TableRow(GamerActivity.this);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1.0f);
            currentRow.setLayoutParams(rowParams);

            for (int x = 0; x < width; x++) {
                // Construct button to insert into current row
                Button currentButton = new Button(GamerActivity.this);
                currentButton.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                currentButton.setTextColor(getColor(R.color.colorPrimary));
                currentButton.setBackgroundTintList(getColorStateList(R.color.colorButtonTint));
                currentButton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f
                ));

                // Add onClick logic to button
                currentButton.setOnClickListener( (View clickedBtn) -> {
                    // Only check scan tile and update UI if clicked btn hasn't already been scanned
                    if (((Button) clickedBtn).getText() == "") {
                        // Find x and y of clicked button
                        TableRow clickedBtnRow = (TableRow) clickedBtn.getParent();
                        int clickedButtonX = clickedBtnRow.indexOfChild(clickedBtn);
                        TableLayout clickedBtnTable = (TableLayout) clickedBtnRow.getParent();
                        int clickedButtonY = clickedBtnTable.indexOfChild(clickedBtnRow);

                        // Scan board at button's position
                        GameBoardTile tileAtButton = gameBoard.scanTile(clickedButtonX, clickedButtonY);

                        // Check if dog was pet and if this dog was already found
                        if (tileAtButton.isDog() & !scannedButtons.contains((Button) clickedBtn)) {
                            // Show the pet dog!
                            // TODO: Play sound effect based on tile's dog voice
                            clickedBtn.setBackgroundTintList(null);
                            switch (tileAtButton.getDogVoice()) {
                                case DEEP:
                                    ((Button) clickedBtn).setBackgroundResource(R.drawable.gamer_dog_deep);
                                    break;
                                case MEDIUM:
                                    ((Button) clickedBtn).setBackgroundResource(R.drawable.gamer_dog_medium);
                                    break;
                                case HIGH:
                                    ((Button) clickedBtn).setBackgroundResource(R.drawable.gamer_dog_high);
                                    break;
                            }
                            // Update display of scanned buttons to reduce hidden dog count
                            for (Button btnToUpdate : scannedButtons) {
                                // Update button text only if it already has text
                                if (!btnToUpdate.getText().toString().isEmpty()) {
                                    // Find x and y of button to update
                                    TableRow btnToUpdateRow = (TableRow) btnToUpdate.getParent();
                                    int btnToUpdateX = btnToUpdateRow.indexOfChild(btnToUpdate);
                                    TableLayout btnToUpdateTable = (TableLayout) btnToUpdateRow.getParent();
                                    int btnToUpdateY = clickedBtnTable.indexOfChild(btnToUpdateRow);

                                    // Update text in btn with nearby dogs in tile
                                    ((Button) btnToUpdate).setText(String.format(Locale.getDefault(), "%d",
                                            gameBoard.getTileDogsNearby(btnToUpdateX, btnToUpdateY)));
                                }
                            }
                        } else {
                            // Add nearby dog text to this button
                            ((Button) clickedBtn).setText(String.format(Locale.getDefault(), "%d",
                                    gameBoard.getTileDogsNearby(clickedButtonX, clickedButtonY)));

                            // Improve visibility of text on dog image buttons
                            if (tileAtButton.isDog()) {
                                clickedBtn.setBackgroundTintList(getColorStateList(R.color.colorScannedDogButtonTint));
                                clickedBtn.setBackgroundTintBlendMode(BlendMode.SRC_ATOP);
                                ((Button) clickedBtn).setTypeface(null, Typeface.BOLD);
                            }
                        }

                        // Add button to scanned buttons
                        if (!scannedButtons.contains((Button) clickedBtn)) {
                            // First click! Add to scanned buttons
                            scannedButtons.add((Button) clickedBtn);
                        }

                        // Update text displays
                        updateTextDisplays();

                        // Check if won
                        if (gameBoard.getDogsFound() == GameOptions.getInstance().getNumDogs()) {
                            // All dogs found! Hooray!
                            GameOptions.getInstance().addScore(gameBoard.getScansUsed());
                            new AlertDialog.Builder(GamerActivity.this)
                                    .setTitle("Congratulations!")
                                    .setMessage(R.string.gamer_text_victory)
                                    .setIcon(R.drawable.gamer_dog_deep)
                                    .setNeutralButton("Back to Main Menu", null)
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            GamerActivity.this.finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                });

                // Insert button into row
                currentRow.addView(currentButton);

            }
            // Insert row into table
            buttonTable.addView(currentRow);
        }
    }

    public static Intent makeIntent(Context c) {
        return new Intent(c, GamerActivity.class);
    }
}