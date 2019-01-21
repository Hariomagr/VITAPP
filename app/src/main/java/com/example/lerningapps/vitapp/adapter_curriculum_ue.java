package com.example.lerningapps.vitapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.Curriculum_UE;

import java.util.ArrayList;

public class adapter_curriculum_ue extends ArrayAdapter<Curriculum_UE> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView title,code,credit;
    }

    public adapter_curriculum_ue(Context context, int resource, ArrayList<Curriculum_UE> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String title = getItem(position).getTitle();
        final String code = getItem(position).getCode();
        final String credit = getItem(position).getCredit();
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.credit = (TextView) convertView.findViewById(R.id.credit);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.title.setText(title);
        holder.code.setText(code);
        holder.credit.setText(credit);
        return convertView;
    }

}