package com.id_card_scanner.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import static com.id_card_scanner.app.CropAndRotate.croppedImage;


public class ExtractTextActivity extends AppCompatActivity {



    ImageView im;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_text);

        mTextView = (TextView) findViewById(R.id.mTextView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                getTextFromImage();

            }
        }, 300);
    }


    public void getTextFromImage(){


        Bitmap bitmap  =croppedImage;
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){



        }else{

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            final SparseArray<TextBlock> items = textRecognizer.detect(frame);

            if (items.size() != 0 ){

                mTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int i=0;i<items.size();i++){
                            TextBlock item = items.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }

//                        String t = stringBuilder.toString().split("valid")[0];
//                        mTextView.setText(t.replaceAll("[^0-9]",""));\
//
                        mTextView.setText(stringBuilder.toString());
                    }
                });
            }



        }


    }
}