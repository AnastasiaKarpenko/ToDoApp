package ws.tilda.anastasia.todoapp;

public class ToDoRepository {

    private static volatile ToDoRepository INSTANCE = new ToDoRepository();

    public synchronized static ToDoRepository get() {
        return INSTANCE;
    }
}
