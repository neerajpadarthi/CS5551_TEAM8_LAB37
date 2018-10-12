package com.example.dell.lab2androidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.EditText;
import android.content.Intent;
import android.util.Log;


import android.speech.RecognizerIntent;
import java.util.Locale;
import android.content.ActivityNotFoundException;
import android.widget.Toast;


import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.firebase.database.collection.LLRBNode;

public class MainPage extends AppCompatActivity {

    String sourceText;
    TextView SentimentView;
   // Context mContext;
    private TextView Text;

    protected static final int RESULT_SPEECH = 1;

    private ImageButton btnSpeak;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        SentimentView = (TextView) findViewById(R.id.txt_Result);
        Text = (TextView) findViewById(R.id.Text);
        editText = (EditText)findViewById(R.id.txt_Email);

        btnSpeak = (ImageButton) findViewById(R.id.mic);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    Text.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    public void logout(View v) {
        Intent redirect = new Intent(MainPage.this, LoginActivity.class);
        startActivity(redirect);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //Text.setText(text.get(0));
                    editText.setText(text.get(0));
                }
                break;
            }

        }
    }

    public void classifyText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.txt_Email);

        sourceText = sourceTextView.getText().toString();
        String getURL = "https://api.uclassify.com/v1/uClassify/Sentiment/classify/?readKey=9QRZ6ZWbVRpH&text="+sourceText;
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        final String PositiveText = jsonResult.getString("positive");
                        final String NegativeText1 = jsonResult.getString("negative");

                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                float positive,negative;
                                positive=Float.parseFloat(PositiveText);
                                negative=Float.parseFloat(NegativeText1);
                                SentimentView.setText("Positive and Negative Sentiment content: "+String.valueOf(positive)+","+String.valueOf(negative));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            SentimentView.setText(ex.getMessage());

        }

    }


    }
