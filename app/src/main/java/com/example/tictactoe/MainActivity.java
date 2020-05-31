package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tictactoe.MyCanvas.MyListener;

import java.util.ArrayList;
import java.util.jar.Attributes;
import com.example.tictactoe.util.VerticalSpacing;


public class MainActivity extends AppCompatActivity {

    MyCanvas mMyCanvas;
    TextView NameHolder1;
    TextView NameHolder2;
    static  String name1,name2;
    RecyclerAdapter mRecyclerAdapter;
    RecyclerView mRecyclerView;
    ArrayList<item> mItems=new ArrayList<>();

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
        setContentView(R.layout.recycler_view);
        mRecyclerView=findViewById(R.id.recyclerView);
        initRecyclerView();
        insertNotes();
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
                NameHolder1.setText(name1);
                NameHolder2.setText(name2);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ljkl");
        setContentView(R.layout.activity_main);
        mMyCanvas=findViewById(R.id.myCanvas);

        NameHolder1=findViewById(R.id.Name1);
        NameHolder2=findViewById(R.id.Name2);
        Intent intent=getIntent();
        name1= intent.getStringExtra("name1");
        name2=intent.getStringExtra("name2");
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
    void insertNotes(){
        for(int k=1;k<200;k++){
            item item=new item();
            item.setName("Title"+k);
            item.setWin(3);
            item.setDraw(4);
            item.setLoss(3);
            mItems.add(item);
        }
        mRecyclerAdapter.notifyDataSetChanged();
    }



}
