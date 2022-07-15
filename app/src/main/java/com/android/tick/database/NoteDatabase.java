package com.android.tick.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import com.android.tick.dao.NoteDao;
import com.android.tick.entities.Note;

@Database(entities = Note.class, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase notesDatabase;


    public static synchronized NoteDatabase getNotesDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase.class,
                    "notes_db"
            ) .fallbackToDestructiveMigration()
                    .build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();
}
