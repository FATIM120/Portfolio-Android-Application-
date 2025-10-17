package com.example.portfolio;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddNoteActivity extends AppCompatActivity {
    private EditText etMatiere, etScore;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etMatiere = findViewById(R.id.etMatiere);
        etScore = findViewById(R.id.etScore);
        btnValider = findViewById(R.id.btnValider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterNote();
            }
        });
    }

    private void ajouterNote() {
        String matiere = etMatiere.getText().toString().trim();
        String scoreStr = etScore.getText().toString().trim();

        if (matiere.isEmpty() || scoreStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int score = Integer.parseInt(scoreStr);
        envoyerNoteAuServeur(matiere, score);
    }

    private void envoyerNoteAuServeur(String matiere, int score) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:3000/api/addNote");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonNote = new JSONObject();
                jsonNote.put("matiere", matiere);
                jsonNote.put("score", score);

                OutputStream os = conn.getOutputStream();
                os.write(jsonNote.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    runOnUiThread(() -> {
                        Toast.makeText(AddNoteActivity.this, "Note ajoutée avec succès !", Toast.LENGTH_SHORT).show();
                        finish(); // Retour à MainActivity après ajout
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(AddNoteActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show());
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(AddNoteActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
