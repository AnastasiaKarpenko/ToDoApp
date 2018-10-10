package ws.tilda.anastasia.todoapp;

import com.google.auto.value.AutoValue;

import java.util.List;

public abstract class Result {

    /* This sets up five Result subclasses that mirror the five Action subclasses.
    The biggest difference — besides the past tense on the class names —
    is that Loaded now has the results of the data load: a List of our model objects. */

    @AutoValue
    public static abstract class Added extends Result {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Modified extends Result {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Deleted extends Result {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Showed extends Result {
        public abstract ToDoModel current();
    }

    @AutoValue
    public static abstract class Loaded extends Result {
        public abstract List<ToDoModel> models();
    }
}
