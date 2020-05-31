package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class StartActivity extends AppCompatActivity {

    TextInputLayout holder1;
    TextInputLayout holder2;
    static int selected1=1,selected2=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        holder1=findViewById(R.id.holder1);
        holder2=findViewById(R.id.holder2);

    }
    private boolean validateHolder1(){
        String name1=holder1.getEditText().getText().toString().trim();
        if(name1.isEmpty()){
            holder1.setError("Field cant be empty");
            return false;
        }
        else if(name1.length()>7){
            holder1.setError("Name too long");
            return false;
        }
        else {
            holder1.setError(null);
            return true;
        }
    }
    private boolean validateHolder2() {
        String name2 = holder2.getEditText().getText().toString().trim();
        if (name2.isEmpty()&&selected2!=2) {
            holder2.setError("Field cant be empty");
            return false;
        } else if (name2.length() > 7&&selected2!=2) {
            holder2.setError("Name too long");
            return false;
        } else {
            holder2.setError(null);
            return true;
        }
    }
    public void OnClicked(View view){
        if(!validateHolder1()|!validateHolder2())
        {
            return;
        }
        String name1=holder1.getEditText().getText().toString().trim();
        String name2=holder2.getEditText().getText().toString().trim();
        if(StartActivity.selected2==2){
            name2="Dumbot2";
        }
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("name1",name1);
        intent.putExtra("name2",name2);
        startActivity(intent);
        finish();
    }


    public void buttonEffect(View view){
        Button button1=findViewById(R.id.doubleP);
        Button button2=findViewById(R.id.singleP);
        if(view.getTag().equals("5"))
        {
            if(selected2==2){
                button1.getBackground().setColorFilter(Color.parseColor("#800000"), PorterDuff.Mode.SRC_ATOP);
                button2.getBackground().clearColorFilter();
                selected2=1;


            }else{
                button1.getBackground().setColorFilter(Color.parseColor("#800000"),PorterDuff.Mode.SRC_ATOP);
            }
            holder2.setVisibility(View.VISIBLE);
            selected1=2;

        }
        if(view.getTag().toString().equals("6")){
            if(selected1==2){
                button2.getBackground().setColorFilter(Color.parseColor("#800000"),PorterDuff.Mode.SRC_ATOP);
                button1.getBackground().clearColorFilter();
                selected1=1;

            }
            else{
                button2.getBackground().setColorFilter(Color.parseColor("#800000"),PorterDuff.Mode.SRC_ATOP);

            }
            selected2=2;
            holder2.setVisibility(View.GONE);
        }

    }
}
