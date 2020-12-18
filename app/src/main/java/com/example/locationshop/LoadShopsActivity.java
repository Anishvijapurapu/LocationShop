


package com.example.locationshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoadShopsActivity extends AppCompatActivity {


    ListView LoadList;

    FirebaseFirestore firebaseFirestore;
    DataModel dataModel;
    List<DataModel> AllList=new ArrayList<>();
    LoadShopsAdapter loadShopsAdapter;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_shops);

        LoadList=findViewById(R.id.MyShopList);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UserEmail = sharedPreferences.getString("Email", "");

        loadShopsAdapter=new LoadShopsAdapter(this,AllList);
        LoadList.setAdapter(loadShopsAdapter);

        loadMethod();


        LoadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel=AllList.get(position);
                Intent i=new Intent(LoadShopsActivity.this,MapsActivity.class);
                i.putExtra("Name",dataModel.getSPName());
                i.putExtra("lat",dataModel.getLat());
                i.putExtra("lng",dataModel.getLng());
                startActivity(i);
            }
        });



    }

    private void loadMethod() {
        progressDialog.show();
        progressDialog.setMessage("Loading.....");

        dataModel=new DataModel("dfg","ertyuu","wetyu","dsfghjk","qERTYU","DERTYUIO",12.2122,17.1234);
        AllList.add(dataModel);
        AllList.removeAll(AllList);
        firebaseFirestore.collection("UserUploadData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot qb:task.getResult())
                    {
                        String Email=qb.getString("UserEmail");
                        String Address=qb.getString("PlaceAddress");
                        String Url=qb.getString("UserImage");
                        String Doc=qb.getId();
                        String Price=qb.getString("PlacePrice");
                        String PName=qb.getString("PlaceName");
                        Double Lat=qb.getDouble("Lat");
                        Double Lng=qb.getDouble("Lng");

                        dataModel=new DataModel(Email,PName,Price,Address,Doc,Url,Lat,Lng);
                        AllList.add(dataModel);
                    }
                    loadShopsAdapter.notifyDataSetChanged();

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