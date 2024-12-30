package io.github.ryuya0124.musical.note.calculator;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        EditText inputBPM = findViewById(R.id.inputBPM);
        Spinner unitSpinner = findViewById(R.id.unitSpinner);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultView = findViewById(R.id.resultView);
        NoteView noteView = findViewById(R.id.noteView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bpmInput = inputBPM.getText().toString();

                if (!bpmInput.isEmpty()) {
                    int bpm = Integer.parseInt(bpmInput);
                    String selectedUnit = unitSpinner.getSelectedItem().toString();

                    double[] noteLengths = calculateNoteLengths(bpm, selectedUnit);
                    noteView.setNoteLengths(noteLengths, selectedUnit);

                    resultView.setText("計算結果が図に表示されました");
                } else {
                    resultView.setText("BPMを入力してください");
                }
            }
        });
    }

    private double[] calculateNoteLengths(int bpm, String unit) {
        double beatDurationMs = 60000.0 / bpm;

        if (unit.equals("s")) {
            beatDurationMs /= 1000.0;
        }

        double wholeNote = beatDurationMs * 4;
        double halfNote = beatDurationMs * 2;
        double quarterNote = beatDurationMs;
        double eighthNote = beatDurationMs / 2;
        double sixteenthNote = beatDurationMs / 4;

        return new double[]{
                wholeNote, halfNote, quarterNote, eighthNote, sixteenthNote
        };
    }
}
