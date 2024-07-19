package com.example.qreklam_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    TextView soruTxt, cevap1, cevap2, cevap3;
    FirebaseStorage storage;
    String dogruCevap;
    VideoView videoView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        soruTxt = findViewById(R.id.textView11);
        cevap1 = findViewById(R.id.textView12);
        cevap2 = findViewById(R.id.textView13);
        cevap3 = findViewById(R.id.textView14);
        storage = FirebaseStorage.getInstance();
        videoView = findViewById(R.id.videoView);
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        StorageReference storageRef = storage.getReference();
        Intent intent = getIntent();
        id = intent.getStringExtra("qr-result");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("ads").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                storageRef.child("Qreklam/videos/"+task.getResult().getString("video")+".mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        videoView.setVideoURI(uri);
                        videoView.start();
                        soruTxt.setText(task.getResult().getString("soru"));
                        cevap1.setText(task.getResult().getString("cevap1"));
                        cevap2.setText(task.getResult().getString("cevap2"));
                        cevap3.setText(task.getResult().getString("cevap3"));
                        dogruCevap = task.getResult().getString("dogrucevap");
                    }
                });

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        }

    }

    public void cevapla1(View view){
        if (cevap1.getText().toString().trim().equals(dogruCevap)) {
            Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show();
            int puan = sharedPreferences.getInt("puan", 0);
            editor.putInt("puan" , puan+100);
            editor.putString("odulkodu", id);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(), "Yanlış", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QReaderActivity.class);
            startActivity(intent);
        }
    }

    public void cevapla2(View view){
        if (cevap2.getText().toString().trim().equals(dogruCevap)) {
            Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show();
            int puan = sharedPreferences.getInt("puan", 0);
            editor.putInt("puan" , puan+100);
            editor.putString("odulkodu", id);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Yanlış", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QReaderActivity.class);
            startActivity(intent);
        }
    }

    public void cevapla3(View view){
        if (cevap3.getText().toString().trim().equals(dogruCevap)) {
            Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show();
            int puan = sharedPreferences.getInt("puan", 0);
            editor.putInt("puan" , puan+100);
            editor.putString("odulkodu", id);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Yanlış", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QReaderActivity.class);
            startActivity(intent);
        }
    }
}