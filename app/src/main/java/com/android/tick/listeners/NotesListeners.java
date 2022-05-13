package com.android.tick.listeners;

import com.android.tick.entities.Note;

public interface NotesListeners {
    void onNoteClicked(Note note, int position);
}
