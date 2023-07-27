package com.example.ottmanager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class profile extends AppCompatActivity {
    DatabaseReference reference;
    TextView name,totalapp,total,phone;
    String phonepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        phonepath=i.getStringExtra("phone");
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        totalapp=findViewById(R.id.totalapps);
        total=findViewById(R.id.total);
        phonepath="6304393058";
        reference= FirebaseDatabase.getInstance().getReference("Users/"+phonepath);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds = task.getResult();
                String s1= String.valueOf(ds.child("Name").getValue());
                name.setText(s1);
                phone.setText(phonepath);
                String s3= String.valueOf(ds.child("Number").getValue());
                totalapp.setText(s3);
                String s4= String.valueOf(ds.child("Total").getValue());
                total.setText(s4);
               }

        });

    }
}