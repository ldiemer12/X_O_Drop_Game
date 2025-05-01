package com.example.x_odropgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.os.CountDownTimer;
import android.media.MediaPlayer;

public class GameView extends View {

    private boolean gameOver = false;

    private final GameActivity activity;
    private Paint paint;
    private int barX, barY, barWidth, barHeight;

    public GameView(GameActivity context) {
        super(context);
        this.activity = context;
        paint = new Paint();

        barWidth = 200;
        barHeight = 40;

        buzzerSound = MediaPlayer.create(context, R.raw.buzzer);

        startCountdown();

    }

    private boolean barInitialized = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if(!barInitialized){
            barX = canvas.getWidth() / 2 - barWidth /2;
            barInitialized = true;
        }

        paint.setColor(Color.BLACK);
        barY = 1800;
        canvas.drawRect(barX, barY, barX + barWidth, barY + barHeight, paint);

        paint.setColor(Color.RED);
        paint.setTextSize(80);
        canvas.drawText("Time: " + timeText, 50, 150, paint);

        if (gameOver){
            paint.setColor(Color.RED);
            paint.setTextSize(150);
            canvas.drawText("GAME OVER", getWidth() / 8, getHeight() /2, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        barX = (int) event.getX() - barWidth / 2;

        if (barX < 0) barX = 0;
        if (barX + barWidth > getWidth()) {
            barX = getWidth() - barWidth;
        }

        invalidate();
        return true;
    }

    private CountDownTimer countDownTimer;
        private long timeLeft = 21000;
        private String timeText = "20";

    private MediaPlayer buzzerSound;
        private Context context;
        private void startCountdown(){
            timeText = String.valueOf(timeLeft / 1000);
            invalidate();
            countDownTimer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long timeUntilGameOver) {
                    timeLeft = timeUntilGameOver;
                    timeText = String.valueOf(timeUntilGameOver / 1000);
                    invalidate();
                }

                @Override
                public void onFinish() {
                    timeText = "0";
                    invalidate();

                    if (buzzerSound != null){
                        buzzerSound.start();
                    }
                    activity.showRestartButton();
                    gameOver = true;

                }
            }.start();

        }

    public void restartGame() {
        timeLeft = 21000;
        timeText = "20";
        barInitialized = false;
        gameOver = false;
        startCountdown();
    }






}