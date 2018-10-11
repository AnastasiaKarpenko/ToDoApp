package ws.tilda.anastasia.todoapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.support.annotation.NonNull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RosterViewModel extends AndroidViewModel {
    public static final int LATEST_ONE = 1;
    private final PublishSubject<Action> actionSubject = PublishSubject.create();
    private final ReplaySubject<ViewState> stateSubject = ReplaySubject.createWithSize(LATEST_ONE);
    private LiveData<ViewState> states;
    private ViewState lastState = ViewState.empty().build();


    public RosterViewModel(@NonNull Application application) {
        super(application);


        Controller controller = new Controller();
        controller.subscribeToActions(actionSubject);

        controller.resultStream()
                .subscribe(result -> {
                    lastState = foldResultIntoState(lastState, result);
                    stateSubject.onNext(lastState);
                }, stateSubject::onError);
        states = LiveDataReactiveStreams
                .fromPublisher(stateSubject.toFlowable(BackpressureStrategy.LATEST));
    }


    public LiveData<ViewState> stateStream() {
        return states;
    }

    public void process(Action action) {
        actionSubject.onNext(action);
    }

    private ViewState foldResultIntoState(@NonNull ViewState state, @NonNull Result result)
            throws Exception {
        if (result instanceof Result.Added) {
            return state.add(((Result.Added) result).model());
        } else {
            throw new IllegalStateException("Unexpected result type: " + result.toString());
        }
    }


}
