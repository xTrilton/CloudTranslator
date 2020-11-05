package com.example.cloudtranslator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView outputtxt;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputtxt= (TextView) findViewById(R.id.text_output);

    }


    //This method is called with the button is pressed and displays microphone dialog box
    public void addSpeech(View v) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

//Start the Activity and wait for the response
        startActivityForResult(intent, REQUEST_CODE);
    }

    //Handle the results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//if the results are okay  get the output
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {

                // retrieve  converted output
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //display the  output
                outputtxt.setText(result.get(0));
            }
        }
    }
}
