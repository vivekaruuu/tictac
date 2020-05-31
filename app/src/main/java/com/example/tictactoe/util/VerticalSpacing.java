package com.example.tictactoe.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacing extends RecyclerView.ItemDecoration {

    int offsetValue;

    public VerticalSpacing(int offsetValue) {
        this.offsetValue = offsetValue;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom=offsetValue;
    }

}
