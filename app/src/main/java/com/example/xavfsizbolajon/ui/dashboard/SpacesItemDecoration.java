package com.example.xavfsizbolajon.ui.dashboard;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SpacesItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }
    }
}
