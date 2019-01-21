package com.example.lerningapps.vitapp.firebase;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.R;

import java.io.File;
import java.util.ArrayList;


public class adapter extends ArrayAdapter<chat_adapter>{
    private Context mcontext;
    int mresource;
    private int STORAGE_PERMISSION_CODEE=1;

    private static class ViewHolder {
        TextView reg,msg;
    }

    public adapter(Context context, int resource, ArrayList<chat_adapter> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences pref = mcontext.getSharedPreferences("login_credentials", 0);
        String reg_no = pref.getString("reg_no", null);
        final String regno = getItem(position).getReg_no();
        final String chat = getItem(position).getChat();
        final String chk = getItem(position).getChk();
        final View result;
        final adapter.ViewHolder holder;
        if (reg_no.equals(regno)) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.send, parent, false);
        } else {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.receive, parent, false);
        }
        holder = new adapter.ViewHolder();
        holder.reg = (TextView) convertView.findViewById(R.id.sender_text_view);
        holder.msg = (TextView) convertView.findViewById(R.id.message_text_view);
        result = convertView;
        convertView.setTag(holder);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chk.equals("0")) {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                        if (readpermission()) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(chat));
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            String xx = URLUtil.guessFileName(chat, null, MimeTypeMap.getFileExtensionFromUrl(chat));
                            request.setTitle(xx);
                            File f = new File(Environment.getExternalStorageDirectory() + "/" + "vitapp");
                            if (!f.exists()) {
                                f.mkdirs();
                            }
                            request.setDestinationInExternalPublicDir("vitapp", xx);
                            DownloadManager downloadManager = (DownloadManager) mcontext.getSystemService(Context.DOWNLOAD_SERVICE);
                            downloadManager.enqueue(request);
                            return;
                        }
                        ActivityCompat.requestPermissions((Activity) mcontext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODEE);
                        Toast.makeText(mcontext,"Please give permission",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(chat));
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        String xx = URLUtil.guessFileName(chat, null, MimeTypeMap.getFileExtensionFromUrl(chat));
                        request.setTitle(xx);
                        File f = new File(Environment.getExternalStorageDirectory() + "/" + "vitapp");
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        request.setDestinationInExternalPublicDir("vitapp", xx);
                        DownloadManager downloadManager = (DownloadManager) mcontext.getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                    }

                }
            }
        });
        if (chk.equals("1")) {
            holder.msg.setText("Attachment: Image");
            holder.msg.setBackgroundColor(Color.GRAY);
            holder.msg.setPadding(10, 0, 50, 0);
        } else if (chk.equals("2")) {
            holder.msg.setText("Attachment: Document");
            holder.msg.setBackgroundColor(Color.GRAY);
            holder.msg.setPadding(10, 0, 50, 0);
        } else {
            holder.msg.setText(chat);
        }
        holder.reg.setText(regno);
        return convertView;
    }
    private Boolean readpermission(){
        int result = ContextCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED)return true;
        return false;
    }

}

