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
        double beatDurationMs = 60000.0 / bpm; // 1拍の長さ(ms)

        double conversionFactor = 1.0;
        if (unit.equals("s")) {
            conversionFactor = 1 / 1000.0; // 秒
        } else if (unit.equals("µs")) {
            conversionFactor = 1000.0; // マイクロ秒
        }

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("ロンガ", formatDuration(beatDurationMs * 4 * 4 * conversionFactor, unit)));
        notes.add(new Note("マキシマ", formatDuration(beatDurationMs * 8 * conversionFactor, unit)));
        notes.add(new Note("倍全音符", formatDuration(beatDurationMs * 4 * 2 * conversionFactor, unit)));
        notes.add(new Note("全音符", formatDuration(beatDurationMs * 4 * conversionFactor, unit)));
        notes.add(new Note("付点2分音符", formatDuration(beatDurationMs * 4 * 3 / 4 * conversionFactor, unit)));
        notes.add(new Note("2分音符", formatDuration(beatDurationMs * 4 * 1 / 2 * conversionFactor, unit)));
        notes.add(new Note("4拍3連", formatDuration(beatDurationMs * 4 / 3 * conversionFactor, unit)));
        notes.add(new Note("付点4分音符", formatDuration(beatDurationMs * 3 / 8 * 4 * conversionFactor, unit)));
        notes.add(new Note("4分音符", formatDuration(beatDurationMs * conversionFactor, unit)));
        notes.add(new Note("付点8分音符", formatDuration(beatDurationMs * 3 / 16 * conversionFactor, unit)));
        notes.add(new Note("2拍3連", formatDuration(beatDurationMs / 6 * conversionFactor, unit)));
        notes.add(new Note("8分音符", formatDuration(beatDurationMs / 8 * conversionFactor, unit)));
        notes.add(new Note("付点16分音符", formatDuration(beatDurationMs * 3 / 32 * conversionFactor, unit)));
        notes.add(new Note("1拍3連", formatDuration(beatDurationMs / 12 * conversionFactor, unit)));
        notes.add(new Note("16分音符", formatDuration(beatDurationMs / 16 * conversionFactor, unit)));
        notes.add(new Note("1拍5連", formatDuration(beatDurationMs / 20 * conversionFactor, unit)));
        notes.add(new Note("1拍6連", formatDuration(beatDurationMs / 24 * conversionFactor, unit)));
        notes.add(new Note("32分音符", formatDuration(beatDurationMs / 32 * conversionFactor, unit)));

        return notes;
    }

    private String formatDuration(double duration, String unit) {
        return String.format("%.2f %s", duration, unit);
    }
}
