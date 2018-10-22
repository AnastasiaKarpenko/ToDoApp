package ws.tilda.anastasia.todoapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RosterViewModel extends AndroidViewModel {
    private LiveData<ViewState> mStates;
    private ViewState mLastState = ViewState.empty().build();
    private static final int LATEST_ONE = 1;
    private final PublishSubject<Action> actionSubject = PublishSubject.create();
    private final ReplaySubject<ViewState> stateSubject = ReplaySubject.createWithSize(LATEST_ONE); //caching the most recent data


    public RosterViewModel(@NonNull Application application) {
        super(application);


        Controller controller = new Controller(application);

        /*
        Last ViewState is blended with new Result and gets a new ViewState via foldResultIntoState().
        To overcome limitations of LiveData and ReactiveDataStreams we needed to make the stream
        itself cache the most recently seen ViewState in stateSubject.
        */
        controller.resultStream()
                .subscribe(result -> {
                    mLastState = foldResultIntoState(mLastState, result);
                    stateSubject.onNext(mLastState);
                }, stateSubject::onError);
        /*
        LiveData mStates is initialized using LifeDataReactiveStreams that comes from the dependency. fromPublisher() method
        creates a LiveData object that knows how to subscribe to RxJava type and pass any received objects to subscribers.
        fromPublisher() methods receives object that implements Publisher interface that is a part of ReactiveStreams initiative.
        stateSubject is converted to Publisher interface via toFlowable(), that requires to supply a BackPressure strategy
        (meaning "what to do if the pipeline gets blocked") and LATEST strategy means to keep the latest items in the pipeline
        and eliminate the older ones if the is a backlog.
        */
        mStates = LiveDataReactiveStreams
                .fromPublisher(stateSubject.toFlowable(BackpressureStrategy.LATEST));

        controller.subscribeToActions(actionSubject);
        process(Action.load());
    }


    public LiveData<ViewState> stateStream() {
        return mStates;
    }

    public void process(Action action) {
        actionSubject.onNext(action);
    }

    private ViewState foldResultIntoState(@NonNull ViewState state,
                                          @NonNull Result result) throws Exception {
        if (result instanceof Result.Added) {
            return state.add(((Result.Added) result).model());
        } else if (result instanceof Result.Modified) {
            return state.modify(((Result.Modified) result).model());
        } else if (result instanceof Result.Deleted) {
            return state.delete(((Result.Deleted) result).model());
        } else if (result instanceof Result.Loaded) {
            List<ToDoModel> models = (((Result.Loaded) result).models());

            return ViewState.builder()
                    .items(models)
                    .current(models.size() == 0 ? null : models.get(0))
                    .isLoaded(true)
                    .build();
        } else if (result instanceof Result.Showed) {
            return state.show(((Result.Showed) result).current());
        } else {
            throw new IllegalStateException("Unexpected result type: " + result.toString());
        }
    }
}
