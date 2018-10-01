package ws.tilda.anastasia.todoapp;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ToDoModel {

    static Builder builder () {
        return new AutoValue_ToDoModel.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract ToDoModel build();
    }


}
