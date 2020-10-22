/*
    Handles UI functionality for the help dialog HelpActivity
*/

package ca.karlstewart.dogseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
        setupAboutTextLinks();
    }

    // Makes links in aboutText clickable
    private void setupAboutTextLinks() {
        TextView aboutText = (TextView) findViewById(R.id.helpAboutText);
        aboutText.setMovementMethod( LinkMovementMethod.getInstance() );
    }

    public static Intent makeIntent(Context c) {
        Intent helpIntent = new Intent(c, HelpActivity.class);
        return helpIntent;
    }
}