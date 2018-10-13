package ws.tilda.anastasia.todoapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.subjects.PublishSubject;

@RunWith(AndroidJUnit4.class)
public class ControllerTest {



    @Test
    public void controller() {
        final Controller controller = new Controller();
        final PublishSubject<Action> actionSubject = PublishSubject.create();
        final LinkedBlockingQueue<Result> receivedResults = new LinkedBlockingQueue<>();

        controller.subscribeToActions(actionSubject);
        controller.resultStream().subscribe(receivedResults::offer);

    }
}
