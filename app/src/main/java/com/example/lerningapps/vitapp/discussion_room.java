package com.example.lerningapps.vitapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.firebase.adapter;
import com.example.lerningapps.vitapp.firebase.chat_adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class discussion_room extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    EditText editText;
    String clas;
    String reg_no;
    ProgressDialog progressDialog,progressDialog1;
    FloatingActionButton floatingActionButton;
    AVLoadingIndicatorView avLoadingIndicatorView1;
    ImageButton imageButton;
    int PICK_IMAGE_REQUEST = 111;
    final static int PICK_PDF_CODE = 2342;
    private int STORAGE_PERMISSION_CODE=23;
    Uri filePath;
    ArrayList<chat_adapter> chat_adapters  =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences the = getApplicationContext().getSharedPreferences("theme", 0);
        String theme = the.getString("theme",null);
        if(theme.equals("app"))setTheme(R.style.AppTheme);
        else if(theme.equals("red"))setTheme(R.style.red);
        else if(theme.equals("purple"))setTheme(R.style.purple);
        else if(theme.equals("deeporange"))setTheme(R.style.deep_orange);
        else if(theme.equals("yellow"))setTheme(R.style.yellow);
        else if(theme.equals("green"))setTheme(R.style.green);
        else if(theme.equals("teal"))setTheme(R.style.teal);
        else if(theme.equals("pink"))setTheme(R.style.pink);
        else if(theme.equals("deeppurple"))setTheme(R.style.deep_purple);
        else if(theme.equals("orange"))setTheme(R.style.orange);
        else if(theme.equals("grey"))setTheme(R.style.grey);
        else if(theme.equals("bluegrey"))setTheme(R.style.bluegrey);
        setContentView(R.layout.activity_discussion_room);

        progressDialog1=new ProgressDialog(this,R.style.alert);
        progressDialog1.setCancelable(false);
        avLoadingIndicatorView1=new AVLoadingIndicatorView(discussion_room.this,null,50,50);
        avLoadingIndicatorView1.setIndicator("BallSpinFadeLoaderIndicator");
        progressDialog1.setIndeterminate(true);

        progressDialog = new ProgressDialog(this);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Chat Room");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        clas = getIntent().getStringExtra("class");
        imageButton=(ImageButton)findViewById(R.id.file);
        editText=(EditText)findViewById(R.id.input_edit_text);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.sendButton);
        listView=(ListView)findViewById(R.id.chat_list);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
        reg_no = pref.getString("reg_no",null);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatdata = editText.getText().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(clas).child("Chat_Data");
                String id = databaseReference.push().getKey();
                chat_adapter chatAdapter = new chat_adapter(reg_no,chatdata,"0",clas);
                if(!chatdata.isEmpty()) {
                    databaseReference.child(id).setValue(chatAdapter);
                    editText.setText("");
                }
            }
        });
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(discussion_room.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        FirebaseDatabase.getInstance().getReference(clas).child("Chat_Data")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        chat_adapters.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String regs=snapshot.child("reg_no").getValue(String.class);
                            String chats=snapshot.child("chat").getValue(String.class);
                            String chk=snapshot.child("chk").getValue(String.class);
                            String clas=snapshot.child("clas").getValue(String.class);
                            chat_adapter chatAdapter = new chat_adapter(regs,chats,chk,clas);
                            chat_adapters.add(chatAdapter);
                        }
                        adapter adapter = new adapter(discussion_room.this,R.layout.send,chat_adapters);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (readpermission()) {
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(discussion_room.this);
                        alt_bld.setTitle("Select file format");
                        final String[] values = new String[]{"Image", "Document"};
                        alt_bld.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_PICK);
                                    startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

                                } else if (which == 1) {
                                    Intent intent = new Intent();
                                    intent.setType("application/pdf");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
                                }
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alt_bld.create();
                        alertDialog.show();
                        return;
                    }
                    requestpermission();
                }
                else {
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(discussion_room.this);
                    alt_bld.setTitle("Select file format");
                    final String[] values = new String[]{"Image", "Document"};
                    alt_bld.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

                            } else if (which == 1) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
                            }
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alt_bld.create();
                    alertDialog.show();
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            String scheme = filePath.getScheme();
            int dataSize=0;
            if(scheme.equals(ContentResolver.SCHEME_CONTENT))
            {
                try {
                    InputStream fileInputStream=getApplicationContext().getContentResolver().openInputStream(filePath);
                    dataSize = fileInputStream.available();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(scheme.equals(ContentResolver.SCHEME_FILE))
            {
                String path = filePath.getPath();
                try {
                    File f = new File(path);
                    dataSize=Integer.parseInt(String.valueOf(f.length()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(dataSize<3732931) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(discussion_room.this);
                builder1.setMessage("Send Image");
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, final int id) {
                                progressDialog1.show();
                                progressDialog1.setContentView(avLoadingIndicatorView1);
                                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(clas).child("Chat_Data");
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference(clas).child("Chat_Data");
                                final String idd = databaseReference.push().getKey();
                                storageReference.child(idd).putFile(filePath)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                chat_adapter chatAdapter = new chat_adapter(reg_no, taskSnapshot.getDownloadUrl().toString(), "1",clas);
                                                databaseReference.child(idd).setValue(chatAdapter);
                                                progressDialog1.dismiss();
                                                Toast.makeText(getApplicationContext(), "File Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog1.dismiss();
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                                progressDialog1.setMessage(String.valueOf(progress));
                                            }
                                        });
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            else{Toast.makeText(getApplicationContext(),"File cannot be greater than 10MB",Toast.LENGTH_SHORT).show();}
        }
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            String scheme = filePath.getScheme();
            int dataSize = 0;
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                try {
                    InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(filePath);
                    dataSize = fileInputStream.available();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                String path = filePath.getPath();
                try {
                    File f = new File(path);
                    dataSize = Integer.parseInt(String.valueOf(f.length()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (dataSize < 3732931) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(discussion_room.this);
                builder1.setMessage("Send Document");
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, final int id) {
                                progressDialog1.show();
                                progressDialog1.setContentView(avLoadingIndicatorView1);
                                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(clas).child("Chat_Data");
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference(clas).child("Chat_Data");
                                final String idd = databaseReference.push().getKey();
                                storageReference.child(idd).putFile(filePath)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                chat_adapter chatAdapter = new chat_adapter(reg_no, taskSnapshot.getDownloadUrl().toString(), "2",clas);
                                                databaseReference.child(idd).setValue(chatAdapter);
                                                progressDialog1.dismiss();
                                                Toast.makeText(getApplicationContext(), "File Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog1.dismiss();
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                                progressDialog1.setMessage(String.valueOf(progress));
                                            }
                                        });
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            else{Toast.makeText(getApplicationContext(),"File size cannot be greater than 10MB",Toast.LENGTH_SHORT).show();}
        }
    }
    public  Boolean readpermission(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED)return true;
        return false;
    }
    public void requestpermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(discussion_room.this);
                alt_bld.setTitle("Select file format");
                final String[] values = new String[]{"Image","Document"};
                alt_bld.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

                        }
                        else if(which==1){
                            Intent intent = new Intent();
                            intent.setType("application/pdf");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alt_bld.create();
                alertDialog.show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Please give the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }
}