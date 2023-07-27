package com.example.ottmanager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    EditText user,pass;
    String phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent ibs=new Intent(this,Firebasepushnotification.class);
        startService(ibs);
        Button b=findViewById(R.id.button);
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        Button sign=findViewById(R.id.signup);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,signup.class);
                startActivity(i);
            }
        });
        b.setOnClickListener(view -> {
           phone= String.valueOf(user.getText());
            password= String.valueOf(pass.getText());
            if(phone!=null && password!=null) {
                reference = FirebaseDatabase.getInstance().getReference("Users/" + phone);
                reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot ds = task.getResult();
                        String s1 = String.valueOf(ds.child("Password").getValue());
                        if (s1.equals(password)) {
                            Intent i = new Intent(MainActivity.this, mysubcriptions.class);
                            i.putExtra("phone", phone);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
            else {
                Toast.makeText(this, "user cant be empty", Toast.LENGTH_SHORT).show();
            }

    });



    }
    }
