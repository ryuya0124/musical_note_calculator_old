package io.github.ryuya0124.musical.note.calculator;

public class Note {
    private String name;
    private String duration;

    public Note(String name, String duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }
}
