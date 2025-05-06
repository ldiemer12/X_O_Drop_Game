package com.example.x_odropgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.os.CountDownTimer;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    private Bar bar;

    private int score = 0;

    private final int FRAME_RATE = 16;
    private final Runnable frameRunnable = new Runnable() {
        @Override
        public void run() {
            if (!gameOver) {
                invalidate();
                postDelayed(this, FRAME_RATE);
            }
        }
    };

    private ArrayList<FallingObject> fallingObjects = new ArrayList<>();
    private Random random = new Random();
    private boolean gameOver = false;

    private final GameActivity activity;
    private Paint paint;

    private long lastSpawnTime = 0;

    public GameView(GameActivity context) {
        super(context);
        this.activity = context;
        paint = new Paint();

        post(() -> bar = new Bar(getWidth(), getHeight()));


        buzzerSound = MediaPlayer.create(context, R.raw.buzzer);

        startCountdown();
        postDelayed(frameRunnable, FRAME_RATE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if (bar == null) {
            bar = new Bar(getWidth(), getHeight());
        }
        bar.draw(canvas, paint);


        paint.setColor(Color.BLACK);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        canvas.drawText("Time: " + timeText, 50, 150, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score: " + score, getWidth() - 50, 150, paint);

        for (FallingObject obj : fallingObjects) {
            obj.fall(50);

            if (bar != null && bar.checkCollision(obj.x, obj.y)) {
                if (obj.isO) {
                    score += 10;
                } else {
                    score -= 5;
                }
                obj.y = getHeight() + 100;
            }

            obj.draw(canvas, paint);

        }
        fallingObjects.removeIf(obj -> obj.y > getHeight());

        if (gameOver) {
            paint.setColor(Color.RED);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(150);
            canvas.drawText("GAME OVER", getWidth() / 2, getHeight() / 2, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bar != null) {
            bar.moveTo((int) event.getX());
        }

        invalidate();
        return true;
    }

    private CountDownTimer countDownTimer;
    private long timeLeft = 21000;
    private String timeText = "20";

    private MediaPlayer buzzerSound;

    private void startCountdown() {
        timeText = String.valueOf(timeLeft / 1000);
        invalidate();
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long timeUntilGameOver) {
                timeLeft = timeUntilGameOver;
                timeText = String.valueOf(timeUntilGameOver / 1000);


                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSpawnTime > 800) {
                    spawnFallingObject();
                    spawnFallingObject();
                }


                invalidate();
            }

            @Override
            public void onFinish() {
                timeText = "0";
                invalidate();

                if (buzzerSound != null) {
                    buzzerSound.start();
                }
                activity.showRestartButton();
                gameOver = true;
                removeCallbacks(frameRunnable);

            }
        }.start();

    }

    public void restartGame() {
        timeLeft = 21000;
        timeText = "20";
        if (bar != null) bar.reset();
        gameOver = false;
        score = 0;
        fallingObjects.clear();
        startCountdown();
        postDelayed(frameRunnable, FRAME_RATE);
    }

    private void spawnFallingObject() {
        int screenWidth = getWidth();
        for (int i = 0; i < 3; i++) {

            int objectX = random.nextInt(screenWidth);
            int objectY = 0;
            int objectSize = 50;
            boolean isO = random.nextBoolean();

            fallingObjects.add(new FallingObject(objectX, objectY, objectSize, isO));
        }
    }


}