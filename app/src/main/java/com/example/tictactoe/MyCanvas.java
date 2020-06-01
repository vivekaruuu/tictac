package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class MyCanvas extends View {

    Paint mBackGroundPaint;
    Paint mLinePaint;
    Paint mFinishPaint;
    Paint mBorderPaint;
    static float w,h;
    static float[] arrW,arrH;
    static int[][] mSelectedBoxes;
    static int mTurn=1;
    static int mFinished=0;
    static int[][][] winningPositions = {{{1,1}, {1, 2}, {1,3}},{{2,1}, {2, 2}, {2,3}},{{3,1}, {3, 2}, {3,3}},{{1,2}, {2, 2}, {3,2}},{{1,1}, {2, 1}, {3,1}},{{1,3}, {2, 3}, {3,3}},{{3,1}, {2, 2}, {1,3}},{{1,1}, {2, 2}, {3,3}}};
    float midHs;
    float midWs;
    float midHe;
    float midWe;
    static int ch=0;
    int stop=0;
    static int computerFirst=1;
    MyListener myListener ;
    int mSwitchValue;

    static ArrayList<shapes> mAllShapes=new ArrayList<shapes>();
    public MyCanvas(Context context) {
        super(context);
        init(null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    public void init(AttributeSet attributeSet){

        mBackGroundPaint=new Paint();
        mLinePaint=new Paint();
        mFinishPaint=new Paint();
        mBorderPaint=new Paint();
        mFinishPaint.setColor(Color.BLACK);
        mFinishPaint.setStrokeWidth(30);
        mFinishPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackGroundPaint.setColor(Color.parseColor("#ffbb00"));
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(20);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setColor(Color.BLACK);
        mBorderPaint.setStrokeWidth(40);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        arrH=new float[4];
        arrW=new float[4];
        if(mSelectedBoxes==null) {
            mSelectedBoxes = new int[3][3];
        }
        arrH[0]=0;arrW[0]=0;
        arrW[1]=w/3;arrW[2]=w*2/3;
        arrH[1]=h/3;arrH[2]=h*2/3;
        arrH[3]=h;arrW[3]=w;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(mBackGroundPaint);
        canvas.drawRect(0,0,arrW[3],arrH[3],mBorderPaint);
        canvas.drawLine(arrW[1],60,arrW[1],h-60,mLinePaint);
        canvas.drawLine(arrW[2],60,arrW[2],h-60,mLinePaint);
        canvas.drawLine(50,arrH[1],w-50,arrH[1],mLinePaint);
        canvas.drawLine(50,arrH[2],w-50,arrH[2],mLinePaint);
        for (shapes s : mAllShapes) {
            canvas.drawPath(s.path, s.paint);
        }
        if(mFinished==1) {
            canvas.drawLine(midWs,midHs,midWe,midHe,mFinishPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for(int k=1;k<4;k++)
                {
                    if(y<arrH[k]&&y>arrH[k-1]){
                        for(int i=1;i<4;i++){
                            if(x<arrW[i]&&x>arrW[i-1]){
                                if(mSelectedBoxes[k-1][i-1]!=0){
                                    return true;
                                }
                                ch++;
                                if(mTurn==1) {
                                    mSelectedBoxes[k - 1][i - 1] = 1;
                                    mTurn=2;
                                    CreateCross(i,k);
                                    checker();
                                    if(StartActivity.selected2==2&&ch<9&&stop==0){
                                        Algorithm();
                                        checker();
                                    }

                                    return true;

                                }else {
                                    mSelectedBoxes[k - 1][i - 1] = 2;
                                    mTurn=1;
                                    CreateCircle(i,k);
                                    checker();
                                    if(StartActivity.selected2==2&&ch<9&&stop==0){
                                        Algorithm();
                                        checker();
                                    }
                                    return true;

                                }

                            }
                        }
                    }
                }



        }
        return true;
    }
//    public void drawFinish(int y1,int x1,int y3, int x3){
//        midHs=arrH[y1];//+h/6;
//        midWs=arrW[x1-1];//+w/6;
//        midHe=arrH[y3-1];//+h/6;
//        midWe=arrW[x3];//+w/6;
//        Log.d(TAG, "drawFinish: "+x1+" "+y1+" "+x3+" "+y3);
//        mFinished=1;
//        invalidate();
//
//    }
    public void checker() {
        int x=0;
        for (int[][] winningPosition : winningPositions) {
            if (mSelectedBoxes[winningPosition[0][0]-1][winningPosition[0][1]-1]== mSelectedBoxes[winningPosition[1][0]-1][winningPosition[1][1]-1] && mSelectedBoxes[winningPosition[2][0]-1][winningPosition[2][1]-1] == mSelectedBoxes[winningPosition[0][0]-1][winningPosition[0][1]-1] && mSelectedBoxes[winningPosition[0][0]-1][winningPosition[0][1]-1] != 0) {
                MediaPlayer mediaPlayer;
                stop=1;
                if(mTurn==2) {
                    mediaPlayer=MediaPlayer.create(getContext(),R.raw.win1);
                    mediaPlayer.start();
                    Toast.makeText(getContext(), String.format("%s won", MainActivity.name1),Toast.LENGTH_LONG).show();

                    if(MainActivity.name1.equals("Dumbot2")){
                        mSwitchValue=3;
                    }
                    else {
                        mSwitchValue=1;
                    }
                    DelayFunction();


                  //  drawFinish(winningPosition[0][0],winningPosition[0][1],winningPosition[2][0],winningPosition[2][1]);
                }
                else {
                    mediaPlayer=MediaPlayer.create(getContext(),R.raw.win2);
                    mediaPlayer.start();
                    Toast.makeText(getContext(), String.format("%s won", MainActivity.name2),Toast.LENGTH_LONG).show();

                    if(MainActivity.name2.equals("Dumbot2")){
                        mSwitchValue=3;
                    }
                    else {
                        mSwitchValue=1;
                    }
                    DelayFunction();
                    //drawFinish(winningPosition[0][0],winningPosition[0][1],winningPosition[2][0],winningPosition[2][1]);

                }

            }
        }

        if(ch==9){
            stop=1;
            MediaPlayer mediaPlayer;
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.draw);
            mediaPlayer.start();
            Toast.makeText(getContext(),"Draw!",Toast.LENGTH_LONG).show();
            mSwitchValue=2;
            DelayFunction();

        }

    }
    private void DelayFunction() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAllShapes.clear();
                mSelectedBoxes=new int[3][3];
                mTurn=1;
                stop=0;
                ch=0;
                String temp=MainActivity.name1;
                MainActivity.name1=MainActivity.name2;
                MainActivity.name2=temp;
                myListener.updateMyText();
                invalidate();

                if(StartActivity.selected2==2){
                    int check=0;
                   for(item k:MainActivity.mItems){
                       if(k.getName().equals(MainActivity.orgName))
                       {
                           check=1;
                           switch (mSwitchValue) {
                               case 1:
                                   k.setWin(k.getWin() + 1);
                                   break;
                               case 2:
                                   k.setDraw(k.getDraw() + 1);
                                   break;
                               case 3:
                                   k.setLoss(k.getLoss() + 1);
                                   break;
                           }
                           myListener.updateMyText();
                       }
                   }
                   if(check==0) {
                       item item = new item();

                       item.setName(MainActivity.orgName);
                       switch (mSwitchValue) {
                           case 1:
                               item.setWin(item.getWin() + 1);
                               break;
                           case 2:
                               item.setDraw(item.getDraw() + 1);
                               break;
                           case 3:
                               item.setLoss(item.getLoss() + 1);
                               break;
                       }
                       MainActivity.mItems.add(item);
                       myListener.updateMyText();
                   }
                    saveData();
                }
                if(computerFirst==1&&StartActivity.selected2==2){
                    mSelectedBoxes[1][1] = 1;
                    mTurn=2;
                    ch++;
                    computerFirst=2;

                    CreateCross(2,2);

                }else {
                    computerFirst=1;
                }
                Log.d(TAG, "run: "+computerFirst);

            }
        }, 1500);
    }
    public void saveData(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("com.example.tictactoe",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(MainActivity.mItems);
        editor.putString("items",json);
        editor.apply();
    }

    public void CreateCircle(int x,int y){
        float midH=arrH[y-1]+h/6;
        float midW=arrW[x-1]+w/6;

        mAllShapes.add(new shapes());
        mAllShapes.get(mAllShapes.size()-1).path.addCircle(midW,midH,w/12, Path.Direction.CW);
        invalidate();

    }
    public void CreateCross(int x,int y){
        float midH=arrH[y-1]+h/6;
        float midW=arrW[x-1]+w/6;

        mAllShapes.add(new shapes());
        mAllShapes.get(mAllShapes.size()-1).path.moveTo(midW-w/12,midH-h/12);
        mAllShapes.get(mAllShapes.size()-1).path.lineTo(midW+w/12,midH+h/12);
        mAllShapes.get(mAllShapes.size()-1).path.lineTo(midW,midH);
        mAllShapes.get(mAllShapes.size()-1).path.lineTo(midW+w/12,midH-h/12);
        mAllShapes.get(mAllShapes.size()-1).path.lineTo(midW-w/12,midH+h/12);
        invalidate();


    }
    public void Algorithm() {
        for (int[][] winningPosition : winningPositions) {
            if (mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] == mSelectedBoxes[winningPosition[1][0] - 1][winningPosition[1][1] - 1] && mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] != 0 && mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] == 0) {
                ch++;
                if (mTurn == 1) {
                    mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] = 1;
                    mTurn = 2;
                    CreateCross(winningPosition[2][1], winningPosition[2][0]);
                    invalidate();
                    return;
                } else {
                    mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] = 2;
                    mTurn = 1;
                    CreateCircle(winningPosition[2][1], winningPosition[2][0]);
                    invalidate();
                    return;
                }
            }
            if (mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] == mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] && mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] != 0 && mSelectedBoxes[winningPosition[1][0] - 1][winningPosition[1][1] - 1] == 0) {
                ch++;
                if (mTurn == 1) {
                    mSelectedBoxes[winningPosition[1][0] - 1][winningPosition[1][1] - 1] = 1;
                    mTurn = 2;
                    CreateCross(winningPosition[1][1], winningPosition[1][0]);
                    invalidate();
                    return;
                } else {
                    mSelectedBoxes[winningPosition[1][0] - 1][winningPosition[1][1] - 1] = 2;
                    mTurn = 1;
                    CreateCircle(winningPosition[1][1], winningPosition[1][0]);
                    invalidate();
                    return;
                }
            }
            if (mSelectedBoxes[winningPosition[1][0] - 1][winningPosition[1][1] - 1] == mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] && mSelectedBoxes[winningPosition[2][0] - 1][winningPosition[2][1] - 1] != 0 && mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] == 0) {
                ch++;

                if (mTurn == 1) {
                    mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] = 1;
                    mTurn = 2;
                    CreateCross(winningPosition[0][1], winningPosition[0][0]);
                    invalidate();
                    return;
                } else {
                    mSelectedBoxes[winningPosition[0][0] - 1][winningPosition[0][1] - 1] = 2;
                    mTurn = 1;
                    CreateCircle(winningPosition[0][1], winningPosition[0][0]);
                    invalidate();
                    return;
                }
            }
        }


        int temp=mTurn;
        while(mTurn==temp){
            randomSelect();
        }

    }

public void randomSelect(){
    Random random=new Random();
    int k,i;
    k=1+random.nextInt(3);
    i=1+random.nextInt(3);
    if(mSelectedBoxes[k-1][i-1]==0){
        ch++;
        if(mTurn==1) {
            mSelectedBoxes[k - 1][i- 1]=1;
            mTurn=2;
            CreateCross(i,k);
            invalidate();
            return;
        }
        else{
            mSelectedBoxes[k - 1][i - 1]=2;
            mTurn=1;
            CreateCircle(i,k);
            invalidate();
            return;
        }

    }

}




    public interface MyListener{
        public void updateMyText();
    }

    public MyListener getMyListener() {
        return myListener;
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }
}
