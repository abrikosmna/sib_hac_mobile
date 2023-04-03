package com.example.hakaton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<User> listTemp;
    private DatabaseReference mDateBase;
    private String USERKEY = "User";
    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromDB();
        setOnClickItem();
    }
    private void init()
    {
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        mDateBase = FirebaseDatabase.getInstance().getReference(USERKEY);
    }
    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(listData.size() > 0)listData.clear();
                if(listTemp.size() > 0)listTemp.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    listData.add(user.adress);
                    listTemp.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDateBase.addValueEventListener(vListener);
    }
    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listTemp.get(position);
                Intent i = new Intent(ReadActivity.this, ShowActivity.class);
                i.putExtra(Constant.USER_ADRESS, user.adress);
                i.putExtra(Constant.USER_FLATE, user.flate);
                i.putExtra(Constant.USER_FIO, user.FIO);
                startActivity(i);
            }
        });
    }

}

//package com.example.hakaton;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadActivity extends AppCompatActivity
//{
//    private ListView listView;
//    private ArrayAdapter<String> adapter;
//    private List<String> listData;
//    private List<User> listTemp;
//    private DatabaseReference mDateBase;
//    private String USERKEY = "User";
//    @Override
//    protected  void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.read_layout);
//        init();
//        getDataFromDB();
//        setOnClickItem();
//    }
//    private void init()
//    {
//        listView = findViewById(R.id.listView);
//        listData = new ArrayList<>();
//        listTemp = new ArrayList<>();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        listView.setAdapter(adapter);
//        mDateBase = FirebaseDatabase.getInstance().getReference(USERKEY);
//    }
//    private void getDataFromDB()
//    {
//        ValueEventListener vListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if(listData.size() > 0)listData.clear();
//                if(listTemp.size() > 0)listTemp.clear();
//              for(DataSnapshot ds : dataSnapshot.getChildren())
//              {
//                  User user = ds.getValue(User.class);
//                  assert user != null;
//                  listData.add(user.adress);
//                  listTemp.add(user);
//              }
//              adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        mDateBase.addValueEventListener(vListener);
//    }
//    private void setOnClickItem(){
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                User user = listTemp.get(position);
//                Intent i = new Intent(ReadActivity.this, ShowActivity.class);
//                i.putExtra(Constant.USER_NAME, user.adress);
//                i.putExtra(Constant.USER_SEC_NAME, user.flate);
//                i.putExtra(Constant.USER_SER_NAME, user.FIO);
//                startActivity(i);
//            }
//        });
//    }
//
//}
