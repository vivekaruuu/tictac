package com.example.tictactoe;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class shapes {
    Path path;
    Paint paint;

    shapes(){
        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }
}
