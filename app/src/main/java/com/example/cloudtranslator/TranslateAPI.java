package com.example.cloudtranslator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


public class TranslateAPI extends AppCompatActivity {
        private EditText translateedt;
        private TextView showtranslation;
        Translate translate;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.translate_layout);
           translateedt = findViewById(R.id.text_translate);
            showtranslation = findViewById(R.id.text_translated);
            Button translateButton = findViewById(R.id.btn_translate);
// this button  checks if the user is connected to the internet

            translateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkInternetConnection()) {
//if the internet connection is good  call the getTranslateService method and translate() method which will translate the given input
                        //If there is internet connection, get translate service and start translation:
                        getTranslateService();
                        translate();

                    } else {

                        //if theres is no internet connection  a no connection method will be sent
                        showtranslation.setText(getResources().getString(R.string.no_connection));
                    }

                }
            });
        }


        public void getTranslateService() {
// here we permit everything in order to access network when we call this method in the main thread
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
// opening the  credentials file in the raw folder
            try (InputStream is = getResources().openRawResource(R.raw.credentials)) {

                //Get credentials from the credentials file

                final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

                //Set credentials and get translate service:
                TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
                translate = translateOptions.getService();
//if task is not successful an exception will be given
            } catch (IOException ioe) {
                ioe.printStackTrace();

            }
        }

        public void translate() {

            //Get input text to be translated from the inputToTranslate edittext
            // text taken from the user through the edittext
            String usertext = translateedt.getText().toString();
            Translation translation = translate.translate(usertext, Translate.TranslateOption.targetLanguage("fr"), Translate.TranslateOption.model("base"));
            // the text that has been translated and will be displayed  to the user
            String translatedText = translation.getTranslatedText();

            //Translated text and original text are set to TextViews:
            showtranslation .setText(translatedText);

        }

        public boolean checkInternetConnection() {

            //Check internet connection:
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final boolean b = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
            return b;
        }

    }


