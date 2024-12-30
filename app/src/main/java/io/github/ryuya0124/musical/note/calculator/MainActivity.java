package io.github.ryuya0124.musical.note.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputBPM = findViewById(R.id.inputBPM);
        Spinner unitSpinner = findViewById(R.id.unitSpinner);
        Button calculateButton = findViewById(R.id.calculateButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bpmInput = inputBPM.getText().toString();

                if (!bpmInput.isEmpty()) {
                    int bpm = Integer.parseInt(bpmInput);
                    String selectedUnit = unitSpinner.getSelectedItem().toString();

                    List<Note> notes = calculateNoteLengths(bpm, selectedUnit);
                    NoteAdapter adapter = new NoteAdapter(notes);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private List<Note> calculateNoteLengths(int bpm, String unit) {
        double quarterNoteLengthMs = 60000.0 / bpm; // 4分音符の長さ（ms）

        // 単位変換ファクター
        double conversionFactor = 1.0;
        if (unit.equals("s")) {
            conversionFactor = 1 / 1000.0; // 秒
        } else if (unit.equals("µs")) {
            conversionFactor = 1000.0; // マイクロ秒
        }

        // 各音符の計算
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("マキシマ", formatDuration(calculateNoteLength(quarterNoteLengthMs, 32), conversionFactor, unit)));
        notes.add(new Note("ロンガ", formatDuration(calculateNoteLength(quarterNoteLengthMs, 16), conversionFactor, unit)));
        notes.add(new Note("倍全音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 4 * 2), conversionFactor, unit)));
        notes.add(new Note("全音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 4), conversionFactor, unit)));
        notes.add(new Note("付点2分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 2, true), conversionFactor, unit)));
        notes.add(new Note("2分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 2), conversionFactor, unit)));
        notes.add(new Note("4拍3連", formatDuration(calculateNoteLength(quarterNoteLengthMs, 4 / 3.0), conversionFactor, unit)));
        notes.add(new Note("付点4分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1, true), conversionFactor, unit)));
        notes.add(new Note("4分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1), conversionFactor, unit)));
        notes.add(new Note("付点8分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 2.0, true), conversionFactor, unit)));
        notes.add(new Note("2拍3連", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 1.5), conversionFactor, unit)));
        notes.add(new Note("8分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 2.0), conversionFactor, unit)));
        notes.add(new Note("付点16分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 4.0, true), conversionFactor, unit)));
        notes.add(new Note("1拍3連", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 3.0), conversionFactor, unit)));
        notes.add(new Note("16分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 4.0), conversionFactor, unit)));
        notes.add(new Note("1拍5連", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 5.0), conversionFactor, unit)));
        notes.add(new Note("1拍6連", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 6.0), conversionFactor, unit)));
        notes.add(new Note("32分音符", formatDuration(calculateNoteLength(quarterNoteLengthMs, 1 / 8.0), conversionFactor, unit)));

        return notes;
    }

    private double calculateNoteLength(double quarterNoteLength, double multiplier) {
        return quarterNoteLength * multiplier;
    }

    private double calculateNoteLength(double quarterNoteLength, double multiplier, boolean isDotted) {
        double baseLength = quarterNoteLength * multiplier;
        return isDotted ? baseLength + (baseLength / 2) : baseLength;
    }

    private String formatDuration(double duration, double conversionFactor, String unit) {
        // 時間のフォーマット
        return String.format("%.2f %s", duration * conversionFactor, unit);
    }
}
