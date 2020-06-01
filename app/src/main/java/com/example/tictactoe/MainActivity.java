package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.tictactoe.MyCanvas.MyListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.example.tictactoe.util.VerticalSpacing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {

    MyCanvas mMyCanvas;
    TextView NameHolder1;
    TextView NameHolder2;
    static  String name1,name2;
    RecyclerAdapter mRecyclerAdapter;
    RecyclerView mRecyclerView;
    static int interruptedLeader=1;
    static String orgName;
    static ArrayList<item> mItems=new ArrayList<>();

    private static final String TAG = "MainActivity";
    public void homeClick(View view){
        Intent intent=new Intent(getApplicationContext(),StartActivity.class);
        startActivity(intent);
        finish();
        MyCanvas.mAllShapes.clear();
        MyCanvas.mSelectedBoxes=new int[3][3];
        MyCanvas.mTurn=1;
    }
    public void leaderClick(View view){
        if(interruptedLeader==1) {
            setContentView(R.layout.recycler_view);
            mRecyclerView = findViewById(R.id.recyclerView);
            initRecyclerView();
        }
    }
    public void gameClicked(View view){
        setContentView(R.layout.activity_main);
        NameHolder1=findViewById(R.id.Name1);
        NameHolder2=findViewById(R.id.Name2);
        NameHolder1.setText(name1);
        NameHolder2.setText(name2);
        mMyCanvas=findViewById(R.id.myCanvas);
        mMyCanvas.setMyListener(new MyListener() {
            @Override
            public void updateMyText() {
                mRecyclerAdapter.notifyDataSetChanged();
                NameHolder1.setText(name1);
                NameHolder2.setText(name2);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyCanvas=findViewById(R.id.myCanvas);
        retrieveData();
        NameHolder1=findViewById(R.id.Name1);
        NameHolder2=findViewById(R.id.Name2);
        Intent intent=getIntent();
        name1= intent.getStringExtra("name1");
        name2=intent.getStringExtra("name2");
        orgName=name1;
        NameHolder1.setText(name1);
        NameHolder2.setText(name2);
        mMyCanvas.setMyListener(new MyListener() {
            @Override
            public void updateMyText() {
                NameHolder1.setText(name1);
                NameHolder2.setText(name2);
            }
        });


    }
    void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            VerticalSpacing itemDecoration = new VerticalSpacing(10);
            mRecyclerView.addItemDecoration(itemDecoration);
            mRecyclerAdapter = new RecyclerAdapter(mItems);
            mRecyclerView.setAdapter(mRecyclerAdapter);

    }
    public void retrieveData(){
        SharedPreferences sharedPreferences=getSharedPreferences( "com.example.tictactoe",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("items",null);
        Type type=new TypeToken<ArrayList<item>>(){}.getType();
        mItems=gson.fromJson(json,type);
    }




}
