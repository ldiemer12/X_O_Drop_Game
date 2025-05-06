package com.example.x_odropgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FallingObject {
    public int x, y;
    public int size;
    public boolean isO;

    public FallingObject(int x, int y, int size, boolean isO) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.isO = isO;
    }

    public void fall(int amount) {
        y += amount;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(isO ? Color.BLUE : Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(100);
        canvas.drawText(isO ? "O" : "X", x, y, paint);
    }
}
