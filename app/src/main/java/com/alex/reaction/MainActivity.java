package com.alex.reaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private ConstraintLayout mContainer;
    private TextView mScoreTextView;
    private int mScore = 0;
    private SharedPreferences mSharedPreferences;
    private TextView mBestScoreTextView;
    private int mBestScore = 0;
    private boolean mIsPaused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getPreferences(MODE_PRIVATE);
        mBestScore = mSharedPreferences.getInt("best_score", 0);
        mContainer = findViewById(R.id.container);
        mScoreTextView = new TextView(this);
        mScoreTextView.setText("Score: 0");
        mScoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        mScoreTextView.setTextColor(Color.BLACK);
        mScoreTextView.setGravity(Gravity.CENTER);
        ConstraintLayout.LayoutParams scoreLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams bestScoreLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        bestScoreLayoutParams.topToBottom = mScoreTextView.getId();
        bestScoreLayoutParams.rightToRight = mContainer.getId();
        bestScoreLayoutParams.topMargin = 24;
        bestScoreLayoutParams.rightMargin = 24;
        mBestScoreTextView.setLayoutParams(bestScoreLayoutParams);
        mContainer.addView(mBestScoreTextView);
        scoreLayoutParams.topToTop = mContainer.getId();
        scoreLayoutParams.rightToRight = mContainer.getId();
        scoreLayoutParams.topMargin = 24;
        scoreLayoutParams.rightMargin = 24;
        mScoreTextView.setLayoutParams(scoreLayoutParams);
        mContainer.addView(mScoreTextView);
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsPaused = !mIsPaused;
                if (mIsPaused) {
                    mHandler.removeCallbacks(mRunnable);
                    pauseButton.setText("Play");
                } else {
                    mHandler.post(mRunnable);
                    pauseButton.setText("Pause");
                }
            }
        });
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            RoundButtonView button = new RoundButtonView(MainActivity.this);
            int buttonSize = (int) getResources().getDimension(R.dimen.button_size); // Change this to the desired button size
            int containerWidth = mContainer.getWidth();
            int containerHeight = mContainer.getHeight();
            int maxX = containerWidth - buttonSize;
            int maxY = containerHeight - buttonSize;
            int x = (int) (Math.random() * maxX);
            int y = (int) (Math.random() * maxY);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(buttonSize, buttonSize);
            layoutParams.leftToLeft = mContainer.getId();
            layoutParams.topToTop = mContainer.getId();
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            button.setLayoutParams(layoutParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mScore++;
                    mScoreTextView.setText("Score: " + mScore);
                    mContainer.removeView(v);
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.post(mRunnable);
                }
            });
            mContainer.addView(button);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mContainer.removeView(button);
                }
            }, 1000);

            mHandler.postDelayed(mRunnable, 1000);
        }
    };
}