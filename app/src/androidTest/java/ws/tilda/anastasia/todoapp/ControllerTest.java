package ws.tilda.anastasia.todoapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ControllerTest {

    @Before
    public void setUp() {
        ToDoDatabase db = ToDoDatabase.get(InstrumentationRegistry.getTargetContext());

        db.todoStore().deleteAll(); //starting from the empty state of the database
    }


    @Test
    public void controller() {
        final Controller controller = new Controller(InstrumentationRegistry.getTargetContext());
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

        /* Add method tests. Create 3 models, add each of them to the repository and confirm with
        tests that we get the equivalent model back from the result */

        final ToDoModel fooModel = ToDoModel.creator()
                .description("foo")
                .notes("hello, world!")
                .build();
        actionSubject.onNext(Action.add(fooModel));

        Result.Added resultAdded = null;
        try {
            resultAdded = (Result.Added) receivedResults.poll(1, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(fooModel, resultAdded.model());

        ToDoModel barModel = ToDoModel.creator().description("bar").build();
        actionSubject.onNext(Action.add(barModel));

        try {
            resultAdded = (Result.Added) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(barModel, resultAdded.model());

        ToDoModel gooModel = ToDoModel.creator()
                .description("goo")
                .isCompleted(true)
                .build();
        actionSubject.onNext(Action.add(gooModel));

        try {
            resultAdded = (Result.Added) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(gooModel, resultAdded.model());

        // Testing modifications

        final ToDoModel mutatedFoo = fooModel.toBuilder().isCompleted(true).build();
        actionSubject.onNext(Action.edit(mutatedFoo));
        Result.Modified resultModified =
                null;
        try {
            resultModified = (Result.Modified) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(mutatedFoo, resultModified.model());

        final ToDoModel mutatedBar = barModel.toBuilder().description("bar!").notes("hi!").build();
        actionSubject.onNext(Action.edit(mutatedBar));
        try {
            resultModified =
                    (Result.Modified) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(mutatedBar, resultModified.model());

        final ToDoModel mutatedGoo = gooModel.toBuilder()
                .description("goo!")
                .isCompleted(false)
                .build();
        actionSubject.onNext(Action.edit(mutatedGoo));
        try {
            resultModified =
                    (Result.Modified) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(mutatedGoo, resultModified.model());

        //Testing deletion

        actionSubject.onNext(Action.delete(barModel));
        Result.Deleted resultDeleted =
                null;
        try {
            resultDeleted = (Result.Deleted) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(barModel, resultDeleted.model());

        actionSubject.onNext(Action.delete(fooModel));
        try {
            resultDeleted =
                    (Result.Deleted) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(fooModel, resultDeleted.model());

        actionSubject.onNext(Action.delete(gooModel));
        try {
            resultDeleted =
                    (Result.Deleted) receivedResults.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(gooModel, resultDeleted.model());
    }
}
