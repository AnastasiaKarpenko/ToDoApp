package ws.tilda.anastasia.todoapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ControllerTest {


    @Test
    public void controller() {
        final Controller controller = new Controller();
        final PublishSubject<Action> actionSubject = PublishSubject.create(); // set up the source for actions
        final LinkedBlockingQueue<Result> receivedResults = new LinkedBlockingQueue<>(); // where results from the controller will go

        controller.subscribeToActions(actionSubject);
        controller.resultStream().subscribe(receivedResults::offer);

        actionSubject.onNext(Action.load()); //emits loaded action into our stream

        // Waits 1 second for results to arrive from the stream
        Result.Loaded resultLoaded = null;
        try {
            resultLoaded = (Result.Loaded) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Confirms that there is no model in the Result cause we start from the empty repository
        assertEquals(0, resultLoaded.models().size());
    }
}
