package com.example.portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notes;
    private Context context;

    public NotesAdapter(Context context) {
        this.context = context;
        this.notes = new ArrayList<>();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.matiereTextView.setText(note.getMatiere());
        holder.scoreTextView.setText(String.valueOf(note.getScore()));

        // Set icon based on whether the note is valid
        holder.noteIcon.setImageResource(note.isValide()
                ? R.drawable.ic_like
                : R.drawable.ic_dislike);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView matiereTextView;
        TextView scoreTextView;
        ImageView noteIcon;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            matiereTextView = itemView.findViewById(R.id.note_matiere);
            scoreTextView = itemView.findViewById(R.id.note_score);
            noteIcon = itemView.findViewById(R.id.note_icon);
        }
    }
}