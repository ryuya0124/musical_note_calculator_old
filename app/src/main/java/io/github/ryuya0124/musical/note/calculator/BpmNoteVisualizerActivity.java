package io.github.ryuya0124.musical.note.calculator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BpmNoteVisualizerActivity extends AppCompatActivity {
    private EditText editTextBpm;
    private Spinner spinnerNoteLength;
    private NoteView noteView;
    private Button buttonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpm_note_visualizer);

        editTextBpm = findViewById(R.id.editTextBpm);
        spinnerNoteLength = findViewById(R.id.spinnerNoteLength);
        noteView = findViewById(R.id.noteView);
        buttonCalculate = findViewById(R.id.buttonCalculate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.note_lengths, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNoteLength.setAdapter(adapter);

        buttonCalculate.setOnClickListener(v -> calculateNoteLength());
    }

    private void calculateNoteLength() {
        String bpmString = editTextBpm.getText().toString();
        if (bpmString.isEmpty()) {
            Toast.makeText(this, "BPMを入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        int bpm = Integer.parseInt(bpmString);
        String noteLength = spinnerNoteLength.getSelectedItem().toString();

        float lengthInSeconds = calculateLengthInSeconds(bpm, noteLength);
        float normalizedLength = normalizeLength(lengthInSeconds);

        noteView.setNoteLength(normalizedLength);
    }

    private float calculateLengthInSeconds(int bpm, String noteLength) {
        float beatsPerSecond = bpm / 60f;
        float lengthInBeats;

        switch (noteLength) {
            case "全音符":
                lengthInBeats = 4;
                break;
            case "2分音符":
                lengthInBeats = 2;
                break;
            case "4分音符":
                lengthInBeats = 1;
                break;
            case "8分音符":
                lengthInBeats = 0.5f;
                break;
            case "16分音符":
                lengthInBeats = 0.25f;
                break;
            default:
                lengthInBeats = 1;
        }

        return lengthInBeats / beatsPerSecond;
    }

    private float normalizeLength(float lengthInSeconds) {
        // Normalize the length to a value between 0 and 1
        // You may need to adjust these values based on your requirements
        float minLength = 0.1f;
        float maxLength = 2f;
        return Math.min(Math.max((lengthInSeconds - minLength) / (maxLength - minLength), 0), 1);
    }
}


