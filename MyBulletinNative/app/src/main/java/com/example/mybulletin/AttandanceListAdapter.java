package com.example.mybulletin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mybulletin.objects.Student;

import java.util.ArrayList;

/**
 * Created by User on 3/14/2017.
 */

public class AttandanceListAdapter extends ArrayAdapter<Student> {

    private static final String TAG = "AttandanceListAdapter";

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView studentName;
        TextView studenId;
        TextView studenData;
    }

    public AttandanceListAdapter(Context context, int resource, ArrayList<Student> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        String Id = getItem(position).getID();
        String tagId = getItem(position).getTagId();
        String tagData = getItem(position).getTagData();
        boolean status = getItem(position).getStatus();

        //Student student = new Student(name,Id,tagId,status);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView studentName = (TextView) convertView.findViewById(R.id.textView1);
        TextView studenId = (TextView) convertView.findViewById(R.id.textView2);
        TextView studenData = (TextView) convertView.findViewById(R.id.textView3);
        studentName.setText(name);
        studenId.setText(Id);

        /*Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(date);*/

        studenData.setText(tagData);


        return convertView;
    }
}

























