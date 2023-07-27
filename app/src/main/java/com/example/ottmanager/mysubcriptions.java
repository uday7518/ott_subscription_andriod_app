package com.example.ottmanager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Objects;

public class mysubcriptions extends AppCompatActivity
{
    ImageButton bt1,bt2;
    DatabaseReference reference,r2;
    String phone;
    int sum=0;
    ArrayList<String> AppNames=new ArrayList<>();
    ArrayList<String> Urls=new ArrayList<>();
    ArrayList<String> plans=new ArrayList<>();
    ArrayList<String> bills=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysubcriptions);

        Intent intent = getIntent();
        phone = (String)intent.getStringExtra("phone");
        reference = FirebaseDatabase.getInstance().getReference("Users/"+phone);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot ds : task.getResult().getChildren()) {
                    String c = ds.getKey();
                    if(!Objects.equals(c, "Name") && !Objects.equals(c, "Password") && !Objects.equals(c, "Total") && !Objects.equals(c, "Number")) {
                        String url = String.valueOf(ds.child("Url").getValue());
                        String bill = String.valueOf(ds.child("Bill").getValue());
                        String plan = String.valueOf(ds.child("Plan").getValue());
                        AppNames.add(c);
                        Urls.add(url);
                        plans.add(plan);
                        bills.add(bill);
                        Log.d("check", c + url + plan + bill);
                    }
                }

                display(AppNames,Urls,plans,bills);
            }
        });

        Button add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mysubcriptions.this,MainActivity2.class);
                i.putExtra("phone",phone);
                startActivity(i);
            }
        });
    }

    private void display(ArrayList<String> appNames, ArrayList<String> urls, ArrayList<String> plans, ArrayList<String> bills)
    {

        for(String i:bills)
        {
            int k= Integer.parseInt(i);
            sum=sum+k;
        }
        r2=FirebaseDatabase.getInstance().getReference("Users/"+phone);
        r2.child("Total").setValue(sum+"");
        r2.child("Number").setValue( bills.size()+"");
        ArrayList<myapps> res = new ArrayList<>();
        for (int i = 0; i < AppNames.size(); i++) {
            myapps obj=new myapps(urls.get(i),appNames.get(i),plans.get(i),bills.get(i));
            res.add(obj);
        }
         mylistapps listAdapter = new mylistapps(mysubcriptions.this,res);
        ListView ls = findViewById(R.id.mysubcriptions);
        ls.setAdapter(listAdapter);
        ls.setClickable(true);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent=new Intent(mysubcriptions.this,Detilas.class);
                intent.putExtra("phone",phone);
                intent.putExtra("name",AppNames.get(i));
                startActivity(intent);
            }
        });
        bt1=findViewById(R.id.imageButton4);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mysubcriptions.this,MainActivity.class);
                startActivity(i);
            }
        });
        bt2=findViewById(R.id.imageButton5);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mysubcriptions.this,profile.class);
                i.putExtra("phone",phone);
                startActivity(i);
            }
        });

    }
}