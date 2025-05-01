package com.example.x_odropgame;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        gameView = new GameView(this);

        restartButton = new Button(this);
        restartButton.setText("Restart Game");
        restartButton.setVisibility(Button.INVISIBLE);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.topMargin = getResources().getDisplayMetrics().heightPixels / 2 + 150;

        restartButton.setLayoutParams(params);

        restartButton.setOnClickListener(v -> {
            gameView.restartGame();
            restartButton.setVisibility(Button.INVISIBLE);
        });

        frameLayout.addView(gameView);
        frameLayout.addView(restartButton);

        setContentView(frameLayout);
    }

    public void showRestartButton(){
        restartButton.setVisibility(Button.VISIBLE);
    }
}
