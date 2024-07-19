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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.color.utilities.Score;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    EditText nametxt, emailtxt, passwordtxt;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        }
        nametxt = findViewById(R.id.nametxt);
        emailtxt = findViewById(R.id.emailtxt);
        passwordtxt = findViewById(R.id.passwordtxt);

        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
        private String writeUidToFirestore(String uid){
            final String[] resultUid = {null};
            firestore.collection("users").document(uid)
                    .set(new HashMap<String, Object>(), SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            resultUid[0] = uid;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            resultUid[0] = "ID YAZILAMADI";
                        }
                    });
            return resultUid[0];
        }
    public void register(View view) {
        adduser();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void adduser() {
        final String fullname = nametxt.getText().toString().trim();
        final String email = emailtxt.getText().toString().trim();
        final String password = passwordtxt.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Bütün Alanları Doldurun", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "Parolanız 6 karakterden Fazla Olmalı", Toast.LENGTH_SHORT).show();
        } else if (fullname.length() < 3) {
            Toast.makeText(this, "Lütfen geçerli bir isim girin", Toast.LENGTH_SHORT).show();
        } else if (!(email.contains("@gmail.com") || email.contains("@outlook.com") || email.contains("yahoo.com"))) {
            Toast.makeText(this, "Lütfen Geçerli Mail Girin ", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        My_Models model = new My_Models(email,fullname,0);
                        firestore.collection("users").document(user.getUid()).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    String message = "Kayıt Başarılı Lütfen Giriş Yapın";
                                    editor.clear();
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("message", message);
                                    intent.putExtra("snack", true);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                }
            });
        }
    }

    private void showBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void go_login(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}