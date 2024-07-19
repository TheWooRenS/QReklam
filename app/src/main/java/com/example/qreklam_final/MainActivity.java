package com.example.qreklam_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText emailtxt,passwordtxt;
    FirebaseAuth auth;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        }
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        emailtxt = findViewById(R.id.emailtxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();

        if (intent.getBooleanExtra("snack",false) == true) {
            showBar(intent.getStringExtra("message"));
        }

        int giris = sharedPreferences.getInt("user-giris", 0);
        if (giris == 808257624) {
            Intent intent1 = new Intent(getApplicationContext(), QReaderActivity.class);
            startActivity(intent1);

        }
        else{

        }
    }
    private void showBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
    private void fetchUserFromFirestore() {
        editor.remove("loginusername");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String userEmail = emailtxt.getText().toString().trim();
        Query query = db.collection("users").whereEqualTo("email", userEmail);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + "!!!!!!!!!!!!!!!!!! => " + document.getString("name"));
                        editor.putString("loginusername", document.getString("email"));
                        editor.putInt("puan", Integer.parseInt(document.getLong("puan").toString()));
                        editor.commit();
                    }
                } else {
                    Log.d("TAG", "Belgeler alınırken hata oluştu: ", task.getException());
                }
            }
        });
    }
    public void girisYap(){
        final String userEmail = emailtxt.getText().toString().trim();
        final String userPassword = passwordtxt.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
            Toast.makeText(getApplicationContext(), "Bütün Alanları Doldurun", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        fetchUserFromFirestore();
                        String message = "Giriş Başarılı Engelsiz Rehber'e Hoş Geldiniz";
                        Intent intent = new Intent(getApplicationContext(), QReaderActivity.class);
                        intent.putExtra("message", message);
                        intent.putExtra("snack", true);
                        startActivity(intent);

                        editor.putString("user-email", userEmail);
                        editor.putString("user-password", userPassword);
                        editor.putString("odulkodu", "yok");
                        editor.putInt("user-giris",808257624);

                        editor.commit();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Mail veya Parola Hatalı", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void login(View view) {
        girisYap();
    }

    public void go_register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}