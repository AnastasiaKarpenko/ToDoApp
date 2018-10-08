package ws.tilda.anastasia.todoapp;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class ViewState {
    public abstract List<ToDoModel> items();

    @Nullable
    public abstract ToDoModel current();

    static Builder builder() {
        return new AutoValue_ViewState.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder items(List<ToDoModel> items);

        abstract Builder current(ToDoModel current);

        abstract ViewState build();
    }
}