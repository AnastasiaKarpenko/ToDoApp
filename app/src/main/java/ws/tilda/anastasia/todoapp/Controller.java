package ws.tilda.anastasia.todoapp;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Controller {
    private final ToDoRepository toDoRepo = ToDoRepository.get();

    //We need to process our actions
    private void processImpl(Action action) {

    }

    public void subscribeToActions(Observable<Action> actionStream) {
        actionStream
                .observeOn(Schedulers.io())
                .subscribe(this::processImpl);
    }


}
