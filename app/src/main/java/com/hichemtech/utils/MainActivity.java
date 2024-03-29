package com.hichemtech.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hichemtech.android_utils.BarcodesGenerators;
import com.hichemtech.android_utils.Forms;
import com.hichemtech.android_utils.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.edittext);
        Button button = findViewById(R.id.ok_btn);
        button.setOnClickListener(view -> {
            if (editText != null && editText.getText() != null) {
                check(editText.getText().toString());
            }
            else{
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void check(String text) {
        ImageView imageView = findViewById(R.id.imageview);
        imageView.setImageBitmap(BarcodesGenerators.generateCodeBar(text));
    }
}