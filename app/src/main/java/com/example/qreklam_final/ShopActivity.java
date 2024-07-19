package com.example.qreklam_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int ucakFiyat, bisikletFiyat, blenderFiyat, hediyeFiyat;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textView = findViewById(R.id.textView2);
        textView.setText("Puan: " + sharedPreferences.getInt("puan", 0000));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        }
        ucakFiyat = 3000;
        bisikletFiyat = 2500;
        blenderFiyat = 1500;
        hediyeFiyat = 1000;
    }

    public void go_scanner(View view) {
        Intent intent = new Intent(getApplicationContext(), QReaderActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }

    public void buyUcak(View view){
        if (sharedPreferences.getInt("puan", 0000) < ucakFiyat){
            Toast.makeText(getApplicationContext(), "Puanınız Yetersiz", Toast.LENGTH_SHORT).show();
        }
        else{

            int oldpoint =sharedPreferences.getInt("puan", 0000);
            editor.putInt("puan",oldpoint-ucakFiyat);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Satın Aldındı", Toast.LENGTH_SHORT).show();
            int point = sharedPreferences.getInt("puan", 0000);
            textView.setText(String.valueOf("Puan: " + point));
        }
    }

    public void buyBisiklet(View view){
        if (sharedPreferences.getInt("puan", 0000) < bisikletFiyat){
            Toast.makeText(getApplicationContext(), "Puanınız Yetersiz", Toast.LENGTH_SHORT).show();
        }
        else{

            int oldpoint =sharedPreferences.getInt("puan", 0000);
            editor.putInt("puan",oldpoint-bisikletFiyat);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Satın Aldındı", Toast.LENGTH_SHORT).show();
            int point = sharedPreferences.getInt("puan", 0000);
            textView.setText(String.valueOf("Puan: " + point));
        }
    }

    public void buyBlender(View view){
        if (sharedPreferences.getInt("puan", 0000) < blenderFiyat){
            Toast.makeText(getApplicationContext(), "Puanınız Yetersiz", Toast.LENGTH_SHORT).show();
        }
        else{

            int oldpoint =sharedPreferences.getInt("puan", 0000);
            editor.putInt("puan",oldpoint-blenderFiyat);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Satın Aldındı", Toast.LENGTH_SHORT).show();
            int point = sharedPreferences.getInt("puan", 0000);
            textView.setText(String.valueOf("Puan: " + point));
        }
    }

    public void buyHediye(View view){
        if (sharedPreferences.getInt("puan", 0000) < hediyeFiyat){
            Toast.makeText(getApplicationContext(), "Puanınız Yetersiz", Toast.LENGTH_SHORT).show();
        }
        else{

            int oldpoint =sharedPreferences.getInt("puan", 0000);
            editor.putInt("puan",oldpoint-hediyeFiyat);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Satın Aldındı", Toast.LENGTH_SHORT).show();
            int point = sharedPreferences.getInt("puan", 0000);
            textView.setText(String.valueOf("Puan: " + point));
        }
    }
}