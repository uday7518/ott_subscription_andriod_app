package com.example.ottmanager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class signup extends AppCompatActivity {
    EditText e1,e2,e3;
    DatabaseReference r2;
    Button b1;
    String username,phone,password;
    Button tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1= findViewById(R.id.username);
        e2= findViewById(R.id.phone);
        e3= findViewById(R.id.password);
        b1= findViewById(R.id.button);
        tv=findViewById(R.id.signup);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username= String.valueOf(e1.getText());
                phone=String.valueOf(e2.getText());
                password=String.valueOf(e3.getText());
                r2 = FirebaseDatabase.getInstance().getReference("Users");
                r2.child(phone).child("Name").setValue(username);
                r2.child(phone).child("Password").setValue(password);
                Toast.makeText(signup.this, "done", Toast.LENGTH_SHORT).show();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(signup.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}