package ws.tilda.anastasia.todoapp;

import java.util.ArrayList;
import java.util.List;

public class ToDoRepository {

    /* This Singleton allows multiple threads to reach the instance. The app is small so Sngleton
    will not cause memory issues.
     */
    private static volatile ToDoRepository INSTANCE = new ToDoRepository();

    private List<ToDoModel> items = new ArrayList<>();

    // Only one thread at a time can get the repository
    public synchronized static ToDoRepository get() {
        return INSTANCE;
    }


    /* This method allows others to get the copy of ArrayList,
     so that only Repository itself could change the original list with data.
     */
    public List<ToDoModel> all() {
        return new ArrayList<>(items);
    }

    public void add(ToDoModel model) {
        items.add(model);
    }

    public void replace(ToDoModel model) {
        for (int i = 0; i < items.size(); i++) {
            if (model.id().equals(items.get(i).id())) {
                items.set(i, model);
            }
        }
    }

    public void delete(ToDoModel model) {
        for (ToDoModel original : items) {
            if (model.id().equals(original.id())) {
                items.remove(original);
                return;
            }
        }
    }

    public ToDoModel find(String id) {
        for (ToDoModel candidate : all()) {
            if (candidate.id().equals(id)) {
                return candidate;
            }
        }
        return null;
    }

}
