package com.example.portfolio;

public class Note {
    private String matiere;
    private int score;
    private boolean valide;

    // Constructeur vide pour Retrofit
    public Note() { }

    public Note(String matiere, int score) {
        this.matiere = matiere;
        this.score = score;
        this.valide = score >= 10;
    }

    public String getMatiere() { return matiere; }
    public int getScore() { return score; }
    public boolean isValide() { return valide; }
}
