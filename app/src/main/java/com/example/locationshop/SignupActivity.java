package com.example.locationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText UserName,Email,Pasword,CPassword;
    android.widget.Button Signup;

    String UserNameStr,StrEmail,StrPassword,StrCPass;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        UserName=findViewById(R.id.UserName);
        Email=findViewById(R.id.Email);
        Pasword=findViewById(R.id.Password);
        CPassword=findViewById(R.id.CPassword);

        Signup=findViewById(R.id.Signup);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        sharedPreferences=getSharedPreferences("Main",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupMethod();
            }
        });
    }

    private void signupMethod() {

        progressDialog.show();
        progressDialog.setMessage("Please wait....");

        UserNameStr = UserName.getText().toString();
        StrEmail = Email.getText().toString();
        StrPassword = Pasword.getText().toString();
        StrCPass = CPassword.getText().toString();

        if (TextUtils.isEmpty(UserNameStr)) {
            Toast.makeText(getApplicationContext(), "Enter User Name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(StrEmail)) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(StrPassword)) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(StrPassword.length()<6)
        {
            Toast.makeText(getApplicationContext(),"Minimum 6 Characters",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            return;
        }

        if (TextUtils.isEmpty(StrCPass)) {
            Toast.makeText(getApplicationContext(), "Enter Confrim Password", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if(!StrCPass.equals(StrPassword))
        {
            Toast.makeText(getApplicationContext(), "Password Not Matching", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        }
        else
            {
                firebaseAuth.createUserWithEmailAndPassword(StrEmail,StrPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            uploadMethod();
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

    private void uploadMethod() {
        progressDialog.show();
        progressDialog.setMessage("Loading.....");

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("UserName",UserNameStr);
        hashMap.put("Email",StrEmail);

        firebaseFirestore.collection("Users").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressDialog.dismiss();

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"SignUp Success",Toast.LENGTH_SHORT).show();
                    editor.putInt("Key",1);
                    editor.putString("Email",StrEmail);
                    editor.apply();
                    editor.commit();
                    finish();
                    startActivity(new Intent(SignupActivity.this,HomeActivity.class));

                }
                else
                {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

            }
        });
    }
}