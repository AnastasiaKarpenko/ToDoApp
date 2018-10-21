package ws.tilda.anastasia.todoapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Nonnull;

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

    public ToDoEntity(@NonNull String id, @NonNull String description, boolean isCompleted, String notes,
                      @NonNull Calendar createdOn) {
        this.id = id;
        this.description = description;
        this.notes = notes;
        this.isCompleted = isCompleted;
        this.createdOn = createdOn;
    }

    public static ToDoEntity fromModel(ToDoModel model) {
        return new ToDoEntity(model.id(), model.description(), model.isCompleted(),
                model.notes(), model.createdOn());
    }

    public ToDoModel toModel() {
        return ToDoModel.builder()
                .id(id)
                .description(description)
                .isCompleted(isCompleted)
                .notes(notes)
                .createdOn(createdOn)
                .build();

    }

    @Dao
    public interface Store {
        @Query("SELECT * FROM todos ORDER BY description ASC")
        List<ToDoEntity> all();

        @Insert
        void insert(ToDoEntity... entities);

        @Update
        void update(ToDoEntity... entities);

        @Delete
        void delete(ToDoEntity... entities);

        @Query("DELETE FROM todos")
        void deleteAll();
    }
}

