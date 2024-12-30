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
        double beatDurationMs = 60000.0 / bpm;

        if (unit.equals("s")) {
            beatDurationMs /= 1000.0;
        }

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("全音符", String.format("%.2f %s", beatDurationMs * 4, unit)));
        notes.add(new Note("2分音符", String.format("%.2f %s", beatDurationMs * 2, unit)));
        notes.add(new Note("4分音符", String.format("%.2f %s", beatDurationMs, unit)));
        notes.add(new Note("8分音符", String.format("%.2f %s", beatDurationMs / 2, unit)));
        notes.add(new Note("16分音符", String.format("%.2f %s", beatDurationMs / 4, unit)));

        return notes;
    }
}
