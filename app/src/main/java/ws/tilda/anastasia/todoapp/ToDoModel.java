package ws.tilda.anastasia.todoapp;

import com.google.auto.value.AutoValue;

import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Nullable;

@AutoValue
public abstract class ToDoModel {

    public abstract String id();

    public abstract boolean isCompleted();

    public abstract String description();

    @Nullable
    public abstract String notes();

    public abstract Calendar createdOn();

    static Builder builder() {
        return new AutoValue_ToDoModel.Builder();
    }

    public static Builder creator() {
        return builder()
                .isCompleted(false)
                .id(UUID.randomUUID().toString())
                .createdOn(Calendar.getInstance());

    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract ToDoModel build();

        abstract Builder id(String id);

        public abstract Builder isCompleted(boolean isCompleted);

        public abstract Builder description(String description);

        public abstract Builder notes(String notes);

        abstract Builder createdOn(Calendar date);
    }


}
