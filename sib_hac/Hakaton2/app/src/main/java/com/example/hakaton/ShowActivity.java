package com.example.hakaton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ShowActivity extends  AppCompatActivity{
    private TextView developer_name, apartment_address;
//    private ImageView imBD;
    private Switch gostSwitch;
    private ListView gostList;
    private EditText comment_error;
    private Button photo_upload;
    private DatabaseReference mDateBase;
    private Uri uploadUri;
    private StorageReference mStorageRef;
    private ImageView image;
    private String USERKEY = "Apartment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layot);
        gostSwitch = findViewById(R.id.gost_switch);
        gostList = findViewById(R.id.list_view_1);
        comment_error = findViewById(R.id.comment);
        photo_upload = findViewById(R.id.upload_photos);

        gostSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gostList.setVisibility(View.VISIBLE);
                    comment_error.setVisibility(View.VISIBLE);
                    photo_upload.setVisibility(View.VISIBLE);
                } else {
                    gostList.setVisibility(View.GONE);
                    comment_error.setVisibility(View.GONE);
                    photo_upload.setVisibility(View.GONE);
                }
            }
        });
        init();
//        getIntentMain();
    }
    private void init(){
        apartment_address = findViewById(R.id.apartment_address);
        developer_name = findViewById(R.id.developer_name);
        comment_error = findViewById(R.id.comment);
        gostList = findViewById(R.id.list_view_1);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImagesDB");
        mDateBase = FirebaseDatabase.getInstance().getReference(USERKEY);

    }
    private void OnCliclSubmit(){
        {
            Intent i = getIntent();
            if(i != null){
               String adress = i.getStringExtra(Constant.USER_ADRESS);
               String flate = i.getStringExtra(Constant.USER_FLATE);
               String FIO = i.getStringExtra(Constant.USER_FIO);
            }
            String id = String.valueOf(mDateBase.push());
            String come_rror = comment_error.getText().toString();
            String  gost_List = gostList.getSelectedItem().toString();
            Apartment newApartment = new Apartment(id, come_rror, gost_List, i.getStringExtra(Constant.USER_ADRESS), i.getStringExtra(Constant.USER_FLATE), i.getStringExtra(Constant.USER_FIO), uploadUri.toString());
            mDateBase.child(id).setValue(newApartment);
        }
    }
    public void onClickChooseImage(View view){
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null){
            if(resultCode == RESULT_OK){
                image.setImageURI(data.getData());
            }
        }
    }

    private void uploadImage(){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child("System.currentTimeMillis()" + "my_image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task){
                uploadUri = task.getResult();
                OnCliclSubmit();
            }
        });
    }
    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("images/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

//    private void getIntentMain(){
//        Intent i = getIntent();
//        if(i != null){
//            Picasso.get().load(i.getStringExtra(Constant.USER_IM)).into(imBD);
//            tvName.setText(i.getStringExtra(Constant.USER_NAME));
//            tvSecName.setText(i.getStringExtra(Constant.USER_SEC_NAME));
//            tvSerName.setText(i.getStringExtra(Constant.USER_SER_NAME));
//        }
//    }
}
