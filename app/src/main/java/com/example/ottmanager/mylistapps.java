package com.example.ottmanager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
public class mylistapps extends ArrayAdapter<myapps> {
    public mylistapps(Context context, ArrayList<myapps> res)
    {
        super(context, R.layout.appitem,res);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        myapps app = (myapps) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appitem, parent, false);
        }
        TextView regno = convertView.findViewById(R.id.appname);
        ImageView imageView=convertView.findViewById(R.id.appicon);
        TextView plan=convertView.findViewById(R.id.plan);
        TextView bill=convertView.findViewById(R.id.bill);
        regno.setText(app.appname);
        Glide.with(convertView).load(app.url).placeholder(R.drawable.baseline_image_24).into(imageView);
        plan.setText(app.plan);
        bill.setText(app.bill);
        return convertView;
    }
}
