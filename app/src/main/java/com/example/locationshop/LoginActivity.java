package com.example.locationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText Email,Password;
    android.widget.Button Login;
    TextView Signup;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String EmailStr,PasswordStr;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);

        Login=findViewById(R.id.Login);

        Signup=findViewById(R.id.Signup);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        sharedPreferences=getSharedPreferences("Main",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginMethod();
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }

    private void loginMethod() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");

        EmailStr=Email.getText().toString();
        PasswordStr=Password.getText().toString();

        if(TextUtils.isEmpty(EmailStr))
        {
            Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if(TextUtils.isEmpty(PasswordStr))
        {
            Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if(PasswordStr.length()<6)
        {
            Toast.makeText(getApplicationContext(),"Enter Min 6 Char",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(EmailStr,PasswordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                        editor.putInt("Key",1);
                        editor.putString("Email",EmailStr);
                        editor.apply();
                        editor.commit();
                        finish();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sharedPreferences.getInt("Key",0)==1)
        {
            finish();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }
}