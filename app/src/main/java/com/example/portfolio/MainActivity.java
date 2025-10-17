package com.example.portfolio;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Le système charge le layout portrait ou paysage selon l'orientation.
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Insertion du fragment formulaire
            FormulaireFragment formFragment = new FormulaireFragment();
            transaction.replace(R.id.container_formulaire, formFragment, "FormulaireFragment");

            // Si le conteneur pour les notes existe, on est en mode paysage
            if (findViewById(R.id.container_notes) != null) {
                isLandscape = true; // Mode paysage détecté
                NotesFragment notesFragment = new NotesFragment();
                transaction.replace(R.id.container_notes, notesFragment, "NotesFragment");
            }

            transaction.commit();
        }

        // Initialisation de Retrofit et de l'API
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Récupération des données depuis l'API
        fetchProfileData();
    }

    private void fetchProfileData() {
        apiService.getProfile("true").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse profile = response.body();

                    // Vérification dans le log du nombre de notes reçues
                    if (profile.getNotes() != null) {
                        Log.d("MainActivity", "Nombre de notes reçues : " + profile.getNotes().size());
                    } else {
                        Log.d("MainActivity", "La liste des notes est null");
                    }

                    // Mise à jour du fragment formulaire
                    FormulaireFragment formFragment =
                            (FormulaireFragment) getSupportFragmentManager().findFragmentByTag("FormulaireFragment");
                    if (formFragment != null) {
                        formFragment.updateForm(
                                profile.getNom(),
                                profile.getPrenom(),
                                profile.getClasse(),
                                profile.getNum(),
                                profile.getRemarque()
                        );
                    }

                    // En mode paysage, mise à jour de la liste des notes
                    if (isLandscape) {
                        NotesFragment notesFragment =
                                (NotesFragment) getSupportFragmentManager().findFragmentByTag("NotesFragment");
                        if (notesFragment != null) {
                            notesFragment.updateNotes(profile.getNotes());
                        } else {
                            Log.d("MainActivity", "NotesFragment non trouvé");
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Échec de connexion à l'API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
