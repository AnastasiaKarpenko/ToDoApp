package ws.tilda.anastasia.todoapp;

import com.google.auto.value.AutoValue;

public abstract class Action {

    @AutoValue
    public static abstract class Add extends Action {
        public abstract ToDoModel model();

    }

    @AutoValue
    public static abstract class Edit extends Action {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Delete extends Action {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Show extends  Action {
        public abstract ToDoModel current();
    }

    public static class Load extends Action {

    }
}
