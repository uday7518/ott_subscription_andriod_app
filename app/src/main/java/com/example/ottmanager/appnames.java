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
public class appnames extends ArrayAdapter<appobj> {
    public appnames(@NonNull Context context, ArrayList<appobj> usersArrayList) {
        super(context, R.layout.top_card,usersArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        appobj app = (appobj) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.top_card, parent, false);
        }
        TextView regno = convertView.findViewById(R.id.app_name);
        ImageView imageView=convertView.findViewById(R.id.app_icon);
        regno.setText(app.appname);
        Glide.with(convertView).load(app.url).placeholder(R.drawable.baseline_image_24).into(imageView);
        return convertView;
    }
}