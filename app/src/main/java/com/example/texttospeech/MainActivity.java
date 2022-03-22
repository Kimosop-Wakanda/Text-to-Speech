package com.example.texttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private ImageButton imageButton;
    private EditText editTextTextPersonName;
    private TextView output1;
    TextToSpeech t1;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
//    private final int REQ_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        output1 = findViewById(R.id.output1);

        //TEXT TO SPEECH
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i == TextToSpeech.SUCCESS) {
                            t1.setLanguage(Locale.UK);
                            t1.setSpeechRate(1.0f);
                            t1.speak(editTextTextPersonName.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                });

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    
    //SPEECH TO TEXT
    private void speak() {
        //Intent to show speech to text dialog
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, Say Something");

        //Start Intent
        try {
            // If no error
            //Show dialog
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            //If error found
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Receive voice input and initialize it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    output1.setText(result.get(0));
                }
                break;
            }
        }
    }
}