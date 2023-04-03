package com.example.hakaton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edTextName, edTextSurname, edTextMiddleName;
    private DatabaseReference mDateBase;
    private String USERKEY = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        edTextName = findViewById(R.id.edTextName);
        edTextSurname = findViewById(R.id.edTextSurname);
        edTextMiddleName = findViewById(R.id.edTextMiddleName);
        mDateBase = FirebaseDatabase.getInstance().getReference(USERKEY);
    }
    public void onClickSave (View view)
    {
        String id = mDateBase.getKey();
        String adress = edTextName.getText().toString();
        String  flate = edTextSurname.getText().toString();
        String FIO = edTextMiddleName.getText().toString();
        User newUser = new User(id, adress, flate, FIO);
        if (!TextUtils.isEmpty(adress) && !TextUtils.isEmpty(flate) && !TextUtils.isEmpty(FIO))
        {
            mDateBase.push().setValue(newUser);
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "пустое поле", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickRead (View view) {
        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }

}


//package com.example.hakaton;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.TextUtils;
//import android.view.View;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText edTextName, edTextSurname, edTextMiddleName;
//    private DatabaseReference mDateBase;
//    private String USERKEY = "User";
//    private StorageReference mStorageRef;
//    private Uri uploadUri;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        init();
//    }
//
//    private void init()
//    {
////        mStorageRef = FirebaseStorage.getInstance().getReference("ImagesDB");
//        edTextName = findViewById(R.id.edTextName);
//        edTextSurname = findViewById(R.id.edTextSurname);
//        edTextMiddleName = findViewById(R.id.edTextMiddleName);
//        mDateBase = FirebaseDatabase.getInstance().getReference(USERKEY);
//    }
//    public void onClickSave (View view)
//    {
////        uploadImage();
//        saveUser();
//    }
//    public void onClickRead (View view) {
//        Intent i = new Intent(MainActivity.this, ReadActivity.class);
//        startActivity(i);
//    }
//
////    public void onClickChooseImage(View view){
////        getImage();
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 1 && data != null && data.getData() != null){
////            if(resultCode == RESULT_OK){
////                image.setImageURI(data.getData());
////            }
////        }
////    }
//
////    private void uploadImage(){
////        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////        byte[] byteArray = baos.toByteArray();
////        final StorageReference mRef = mStorageRef.child("System.currentTimeMillis()" + "my_image");
////        UploadTask up = mRef.putBytes(byteArray);
////        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
////            @Override
////            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
////                return mRef.getDownloadUrl();
////            }
////        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
////            @Override
////            public void onComplete(@NonNull Task<Uri> task){
////                uploadUri = task.getResult();
////                saveUser();
////            }
////        });
////    }
//
//
////    private void getImage(){
////        Intent intentChooser = new Intent();
////        intentChooser.setType("images/*");
////        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(intentChooser, 1);
////    }
//
//    private void saveUser(){
//        String id = mDateBase.push().getKey();
//        String adress = edTextName.getText().toString();
//        String flate = edTextSurname.getText().toString();
//        String FIO = edTextMiddleName.getText().toString();
//        User newUser = new User(id);
//        if (!TextUtils.isEmpty(adress) && !TextUtils.isEmpty(flate) && !TextUtils.isEmpty(FIO))
//        {
//            if (id != null) mDateBase.child(id).setValue(newUser);
//            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(this, "пустое поле", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//}