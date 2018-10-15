package ws.tilda.anastasia.todoapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "todos", indices = @Index(value = "id"))
public class ToDoEntity {
    @PrimaryKey
    @NonNull
    final String id;
    @NonNull
    final String description;
    final String notes;
    final boolean isCompleted;
    @NonNull
    final Calendar createdOn;

    public ToDoEntity(@NonNull String id, @NonNull String description, String notes,
                      boolean isCompleted, @NonNull Calendar createdOn) {
        this.id = id;
        this.description = description;
        this.notes = notes;
        this.isCompleted = isCompleted;
        this.createdOn = createdOn;
    }
}

