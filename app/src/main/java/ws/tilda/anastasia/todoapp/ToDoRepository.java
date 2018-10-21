package ws.tilda.anastasia.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ToDoRepository {

    /* This Singleton allows multiple threads to reach the instance. The app is small so Sngleton
    will not cause memory issues.
     */
    private static volatile ToDoRepository INSTANCE = null;
    private final ToDoDatabase db;

    private ToDoRepository(Context context) {
        db = ToDoDatabase.get(context);
    }

    // Only one thread at a time can get the repository
    public synchronized static ToDoRepository get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ToDoRepository(context.getApplicationContext());
        }
        return INSTANCE;
    }


    /* This method allows others to get the copy of ArrayList,
     so that only Repository itself could change the original list with data.
     */
    public List<ToDoModel> all() {
        List<ToDoEntity> entities = db.todoStore().all();
        ArrayList<ToDoModel> result = new ArrayList<>(entities.size());

        for (ToDoEntity entity : entities) {
            result.add(entity.toModel());
        }
        return result;
    }

    public void add(ToDoModel model) {
        db.todoStore().insert(ToDoEntity.fromModel(model));
    }

    public void replace(ToDoModel model) {
        db.todoStore().update(ToDoEntity.fromModel(model));
    }

    public void delete(ToDoModel model) {
        db.todoStore().delete(ToDoEntity.fromModel(model));
    }


}
