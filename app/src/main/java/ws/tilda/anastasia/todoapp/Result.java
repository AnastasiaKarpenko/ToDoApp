package ws.tilda.anastasia.todoapp;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

public abstract class Result {

    //Factory methods corresponding to the result types

    public static Result added(ToDoModel model) {
        return new AutoValue_Result_Added(model);
    }

    public static Result modified(ToDoModel model) {
        return new AutoValue_Result_Modified(model);
    }

    public static Result deleted(ToDoModel model) {
        return new AutoValue_Result_Deleted(model);
    }

    public static Result showed(ToDoModel current) {
        return new AutoValue_Result_Showed(current);
    }

    public static Result loaded(List<ToDoModel> models) {
        return new AutoValue_Result_Loaded(Collections.unmodifiableList(models));
    }


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
