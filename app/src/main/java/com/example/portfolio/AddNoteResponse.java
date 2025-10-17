package com.example.portfolio;

public class AddNoteResponse {
    private String message;
    private Note note;

    // Getters et setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Note getNote() {
        return note;
    }
    public void setNote(Note note) {
        this.note = note;
    }
}
