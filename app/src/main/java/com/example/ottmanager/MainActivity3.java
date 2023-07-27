package com.example.ottmanager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
public class MainActivity3 extends AppCompatActivity {
    String url,name;
    DatabaseReference reference,r2;
    ImageView imv;
    RadioButton t1,t2,t3;
    RadioGroup pay;
    Button save,b2;
    RadioButton rb;
    String s1,s2,s3,s4,date,plan,phone,open;
    int rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent=getIntent();
        phone = (String)intent.getStringExtra("phone");
        url=intent.getStringExtra("url");
        name=intent.getStringExtra("name");
        imv=findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.baseline_image_24).into(imv);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        b2=findViewById(R.id.button2);
        Button dp=findViewById(R.id.button3);
        TextView t=findViewById(R.id.dateshow);
        save=findViewById(R.id.submit);
        pay=findViewById(R.id.pay);
        reference = FirebaseDatabase.getInstance().getReference("Appnames/"+name+"/bills");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<DataSnapshot> task) {
              DataSnapshot ds = task.getResult();
            s1= String.valueOf(ds.child("1-month").getValue());
            s2= String.valueOf(ds.child("3-months").getValue());
            s3= String.valueOf(ds.child("1-year").getValue());
            s4=String.valueOf(ds.child("details").getValue());
            open=String.valueOf(ds.child("open").getValue());
            Toast.makeText(MainActivity3.this, name, Toast.LENGTH_SHORT).show();
                t1.setText("1-month :"+s1);
                t1.setVisibility(View.VISIBLE);
                t2.setText("2-months :"+s2);
                t2.setVisibility(View.VISIBLE);
                t3.setText("3-months :"+s3);
                t3.setVisibility(View.VISIBLE);}

            });

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                MainActivity3.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                       t.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(s4));
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=pay.getCheckedRadioButtonId();
                rb=findViewById(id);
                plan= String.valueOf(rb.getText());
                String[] k=plan.split(":");
                date= String.valueOf(t.getText());
                r2 = FirebaseDatabase.getInstance().getReference("Users/"+phone);
                r2.child(name).child("Date").setValue(date);
                r2.child(name).child("Plan").setValue(k[0]);
                r2.child(name).child("Bill").setValue(k[1]);
                r2.child(name).child("Url").setValue(url);
                r2.child(name).child("open").setValue(open);
                Toast.makeText(MainActivity3.this, "succesful", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(MainActivity3.this,mysubcriptions.class);
                intent1.putExtra("phone",phone);
                startActivity(intent1);
            }
        });

        }


    }
