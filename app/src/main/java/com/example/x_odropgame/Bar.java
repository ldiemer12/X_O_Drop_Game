package com.example.x_odropgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;


public class Bar {

    private int x, y, width, height;

    private int screenWidth, screenHeight;

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public Bar(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.width = 200;
        this.height = 40;
        reset();
    }

    public void moveTo(int touchX) {
        x = touchX - width / 2;

        if (x < 0) x = 0;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    public boolean checkCollision(int objX, int objY) {
        return objY >= y && objY <= y + height && objX >= x && objX <= x + width;
    }

    public void reset() {
        x = screenWidth / 2 - width / 2;
        y = screenHeight - 250;
    }

    public int getY() {
        return y;
    }

}
