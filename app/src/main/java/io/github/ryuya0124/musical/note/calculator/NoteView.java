package io.github.ryuya0124.musical.note.calculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class NoteView extends View {

    private Paint paint;
    private double[] noteLengths = {};
    private String unit = "ms";
    private float noteLength = 0.5f; // デフォルト値を設定

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
    }

    // 音符の長さデータを設定
    public void setNoteLengths(double[] lengths, String unit) {
        this.noteLengths = lengths;
        this.unit = unit;
        invalidate(); // 再描画
    }


    public void setNoteLength(float length) {
        this.noteLength = length;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();

        // Draw note stem
        canvas.drawLine(width * 0.9f, height * 0.2f, width * 0.9f, height * 0.8f, paint);

        // Draw note head
        canvas.drawOval(width * 0.7f, height * 0.7f, width * 0.9f, height * 0.8f, paint);

        // Draw length indicator
        canvas.drawLine(0, height / 2, width * noteLength, height / 2, paint);
    }


}