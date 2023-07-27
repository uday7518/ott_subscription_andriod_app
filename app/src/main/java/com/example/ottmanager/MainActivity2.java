package com.example.ottmanager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    ImageButton bt;
    DatabaseReference reference;
    ArrayList<String> AppNames=new ArrayList<>();
    ArrayList<String> Urls=new ArrayList<>();
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent ip=getIntent();
        phone = (String)ip.getStringExtra("phone");
        reference = FirebaseDatabase.getInstance().getReference("Appnames");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                      for (DataSnapshot ds : task.getResult().getChildren()) {
                                                          String c = ds.getKey();
                                                          String url= String.valueOf(ds.child("url").getValue());
                                                          AppNames.add(c);
                                                          Urls.add(url);
                                                      }
                                                      display(AppNames,Urls);
                                                  }
                                              });

    }

    private void display(ArrayList<String> AppNames,ArrayList<String> Urls) {
        ArrayList<appobj> res = new ArrayList<>();
        for (int i = 0; i < AppNames.size(); i++) {
            appobj obj=new appobj((AppNames.get(i)),(Urls.get(i)));
            res.add(obj);
        }

        appnames listAdapter = new appnames(MainActivity2.this,res);
        ListView ls = findViewById(R.id.topcardrecyclerview);
        ls.setAdapter(listAdapter);
        ls.setClickable(true);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity2.this, i+"done", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                intent.putExtra("phone",phone);
                intent.putExtra("url",Urls.get(i));
                intent.putExtra("name",AppNames.get(i));
                startActivity(intent);
            }
        });
        bt=findViewById(R.id.imageButton3);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}