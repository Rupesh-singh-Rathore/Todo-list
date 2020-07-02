package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText email,userName,passWord,mobile;
    Button singup;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        mobile = findViewById(R.id.mobile);

        singup = findViewById(R.id.singup);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){


            startActivity(new Intent(getApplicationContext(),todolist.class));
            finish();

        }


        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getText().toString().trim();
                String mpasswordd = passWord.getText().toString().trim();

                if(TextUtils.isEmpty(memail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(mpasswordd)){
                    passWord.setError("Password is required");
                    return;
                }

                if(mpasswordd.length() < 6){
                    passWord.setError("Password must be >= 6 Characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(memail,mpasswordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Registerd Succesfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),todolist.class));
                        }else{

                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}
