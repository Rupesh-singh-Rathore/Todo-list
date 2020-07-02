package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements  View.OnClickListener{

    private ImageButton register;
    private TextView tvLogin;

    EditText lEmail,lpassword;
    Button btnlogin;
    FirebaseAuth fAuth;
    TextView forgetTextLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // register

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

       // user

        tvLogin = findViewById(R.id.Login);

        lEmail= findViewById(R.id.lEmail);

        lpassword= findViewById(R.id.lpassword);

        fAuth = FirebaseAuth.getInstance();

        btnlogin = findViewById(R.id.btnlogin);

        forgetTextLink = findViewById(R.id.forgetPassword);

        if(fAuth.getCurrentUser() != null){


            startActivity(new Intent(getApplicationContext(),todolist.class));
            finish();

        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String lemail = lEmail.getText().toString().trim();
                String lpasswordd = lpassword.getText().toString().trim();

                if(TextUtils.isEmpty(lemail)){
                    lEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(lpasswordd)){
                    lpassword.setError("Password is required");
                    return;
                }

                if(lpasswordd.length() < 6){
                    lpassword.setError("Password must be >= 6 Characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(lemail,lpasswordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),todolist.class));
                        }else {
                            Toast.makeText(login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }

        });

        forgetTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());

                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());

                passwordResetDialog.setTitle("Reset Password ?");

                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link. ");

                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(login.this, "Reset Link Sent To Tour Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Close

                    }
                });

                passwordResetDialog.create().show();

            }
        });

    }
    @Override
    public void onClick(View v) {

        if(v==register){
            Intent intent =new Intent(login.this,Register.class);
            startActivity(intent);
        }

    }
}
