package com.example.ottmanager;
import static android.net.Uri.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Detilas extends AppCompatActivity {
 String phone,name,bill,plan,url,date,currentdate,result,daysBetween,open;
 DatabaseReference reference;
 TextView tv,v1,v2,v3,v4,v5,v6;
 Button openapp,remove;
 ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detilas);
        v1=findViewById(R.id.name);
        v2=findViewById(R.id.plan);
        v3=findViewById(R.id.bill);
        v4=findViewById(R.id.date);
        v5=findViewById(R.id.tilldate);
        v6=findViewById(R.id.days);
        iv=findViewById(R.id.imageview);
        openapp=findViewById(R.id.open);
        remove=findViewById(R.id.remove);
        Intent intent = getIntent();
        phone = (String)intent.getStringExtra("phone");
        name= (String)intent.getStringExtra("name");
        reference= FirebaseDatabase.getInstance().getReference("Users/"+phone+"/"+name);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot ds = task.getResult();
                bill = String.valueOf(ds.child("Bill").getValue());
                date = String.valueOf(ds.child("Date").getValue());
                plan = String.valueOf(ds.child("Plan").getValue());
                open= String.valueOf(ds.child("open").getValue());
                url=String.valueOf(ds.child("Url").getValue());
                String[] p=plan.split("-");
                String[] d=date.split("-");
                int year1= Integer.parseInt(d[2]);
                int month1= Integer.parseInt(d[1]);
                int day1= Integer.parseInt(d[0]);
                LocalDate startDate = LocalDate.of(year1,month1,day1);
                int monthsToAdd = Integer.parseInt(p[0]);
                LocalDate dateAfterMonths = startDate.plusMonths(monthsToAdd);
                result = dateAfterMonths.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Glide.with(getBaseContext()).load(url).into(iv);
                LocalDateTime localDT = LocalDateTime.now();
                currentdate=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDT);
                LocalDate date1 = LocalDate.parse(currentdate);
                LocalDate date2 = LocalDate.parse(result);
                if (date1.isAfter(date2))
                {
                     daysBetween="Expried";
                } else if (date1.isBefore(date2))
                {
                     daysBetween = String.valueOf(ChronoUnit.DAYS.between(date1, date2));
                }
                v1.setText(name);
                v2.setText(plan);
                v3.setText(bill);
                v4.setText(date);
                v5.setText(result);
                v6.setText(daysBetween+"");
            }
            });

remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        deleteData(name);
    }
});
openapp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(Intent.ACTION_VIEW, parse(open));
              startActivity(intent);
           }
       });
    }

    private void deleteData(String name) {
        reference = FirebaseDatabase.getInstance().getReference("Users/"+phone);
        reference.child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Toast.makeText(Detilas.this, "Successfuly Deleted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Detilas.this,mysubcriptions.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            } else {
                Toast.makeText(Detilas.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
}
