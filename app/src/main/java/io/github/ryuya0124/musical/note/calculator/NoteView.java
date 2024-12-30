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

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8f);
    }

    // 音符の長さデータを設定
    public void setNoteLengths(double[] lengths, String unit) {
        this.noteLengths = lengths;
        this.unit = unit;
        invalidate(); // 再描画
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (noteLengths.length == 0) return;

        int width = getWidth();
        int height = getHeight();
        int padding = 20;

        double maxLength = noteLengths[0]; // 最大長さを基準にスケール調整
        int yPosition = padding;

        paint.setTextSize(36f);
        paint.setColor(Color.BLACK);

        // 各音符を描画
        for (int i = 0; i < noteLengths.length; i++) {
            double length = noteLengths[i];
            int barWidth = (int) (width * (length / maxLength));

            // ラベルを描画
            String label = String.format("音符%d: %.2f %s", i + 1, length, unit);
            canvas.drawText(label, padding, yPosition + 40, paint);

            // 棒を描画
            paint.setColor(Color.BLUE);
            canvas.drawRect(padding, yPosition + 50, padding + barWidth, yPosition + 100, paint);

            yPosition += 120; // 次の音符の位置
        }
    }
}