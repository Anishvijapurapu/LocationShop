package com.example.locationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DisplayDataActivity extends AppCompatActivity {

    EditText SPname,price,Address;
    ImageView myImage;
    Button Delete;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    String UserEmail;

    String PlaceNameStr, PriceStr, AddressStr,ImageStr,DocStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        SPname=findViewById(R.id.ShopName);
        price=findViewById(R.id.ProductPrice);
        Address=findViewById(R.id.Address);

        myImage=findViewById(R.id.PImage);
        Delete=findViewById(R.id.Delete);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UserEmail = sharedPreferences.getString("Email", "");

        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        PlaceNameStr=getIntent().getStringExtra("Name");
        PriceStr=getIntent().getStringExtra("Price");
        AddressStr=getIntent().getStringExtra("Address");
        ImageStr=getIntent().getStringExtra("Url");
        DocStr=getIntent().getStringExtra("Document");

        SPname.setText(""+PlaceNameStr);
        price.setText(""+PriceStr);
        Address.setText(""+AddressStr);
        Glide.with(DisplayDataActivity.this).load(""+ImageStr).centerCrop().placeholder(R.drawable.btn_bg).into(myImage);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMethod();
            }
        });
    }

    private void deleteMethod() {

        progressDialog.show();
        progressDialog.setMessage("Deleting File....");
        firebaseFirestore.collection("UserUploadData").document(""+DocStr).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show();
                finish();


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