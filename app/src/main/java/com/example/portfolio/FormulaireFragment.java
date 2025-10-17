package com.example.portfolio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class FormulaireFragment extends Fragment {

    private EditText tvNom, tvPrenom, tvClasse, tvNum, tvRemarque;

    public FormulaireFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulaire, container, false);

        tvNom = view.findViewById(R.id.tv_nom);
        tvPrenom = view.findViewById(R.id.tv_prenom);
        tvClasse = view.findViewById(R.id.tv_classe);
        tvNum = view.findViewById(R.id.tv_num);
        tvRemarque = view.findViewById(R.id.tv_remarque);

        view.findViewById(R.id.btn_appeler).setOnClickListener(v -> {
            String phoneNumber = tvNum.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            } else {
                Toast.makeText(getActivity(), "Entrez un numéro valide", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.btn_enregistrer).setOnClickListener(v -> {
            // Exemple : Appel à l'API pour enregistrer le profil
            Toast.makeText(getActivity(), "Profil enregistré", Toast.LENGTH_SHORT).show();
        });

        // Modification ici : ouverture de AddNoteActivity au lieu d'afficher un Toast.
        view.findViewById(R.id.btn_ajouter_note).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNoteActivity.class);
            startActivity(intent);
        });

        return view;
    }

    // Méthode pour mettre à jour les champs avec les données récupérées depuis l'API
    public void updateForm(String nom, String prenom, String classe, String num, String remarque) {
        tvNom.setText(nom);
        tvPrenom.setText(prenom);
        tvClasse.setText(classe);
        tvNum.setText(num);
        tvRemarque.setText(remarque);
    }
}
