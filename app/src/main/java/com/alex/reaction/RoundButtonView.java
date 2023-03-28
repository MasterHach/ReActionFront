package com.alex.reaction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RoundButtonView extends View {
    private Paint mPaint;

    public RoundButtonView(Context context) {
        super(context);
        init();
    }

    public RoundButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = (int) getResources().getDimension(R.dimen.button_size) / 4;
        canvas.drawCircle(cx, cy, radius, mPaint);
    }
}
