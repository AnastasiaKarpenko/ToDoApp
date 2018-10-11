package ws.tilda.anastasia.todoapp;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Controller {
    private final ToDoRepository toDoRepo = ToDoRepository.get();
    private final PublishSubject<Result> resultSubject = PublishSubject.create();

    //We need to process our actions
    private void processImpl(Action action) {
        if (action instanceof Action.Add) {
            add(((Action.Add) action).model());
        } else if (action instanceof Action.Edit) {
            modify(((Action.Edit) action).model());
        } else if (action instanceof Action.Delete) {
            delete(((Action.Delete) action).model());
        } else if (action instanceof Action.Load) {
            load();
        } else if (action instanceof Action.Show) {
            show(((Action.Show) action).current());
        }
    }

    public void subscribeToActions(Observable<Action> actionStream) {
        actionStream
                .observeOn(Schedulers.io())
                .subscribe(this::processImpl);
    }

    private void add(ToDoModel model) {
        toDoRepo.add(model);
        resultSubject.onNext(Result.added(model));
    }

    private void modify(ToDoModel model) {
        toDoRepo.replace(model);
        resultSubject.onNext(Result.modified(model));
    }

    private void delete(ToDoModel model) {
        toDoRepo.delete(model);
        resultSubject.onNext(Result.deleted(model));
    }

    public Observable<Result> resultStream() {
        return resultSubject;
    }

    private void load() {
        resultSubject.onNext(Result.loaded(toDoRepo.all()));
    }

    private void show(ToDoModel current) {
        resultSubject.onNext(Result.showed(current));
    }


}
