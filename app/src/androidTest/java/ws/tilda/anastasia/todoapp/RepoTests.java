package ws.tilda.anastasia.todoapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class RepoTests {
    private ToDoRepository repo;

    @Before
    public void setUp() {
        repo = new ToDoRepository();
    }

    @Test
    public void getAll() {
        assertEquals(3, repo.all().size());
    }

    @Test
    public void add() {
        ToDoModel model = ToDoModel.creator()
                .isCompleted(true)
                .description("foo")
                .build();

        repo.add(model);

        List<ToDoModel> models = repo.all();

        assertEquals(4, models.size());
        assertThat(models, hasItem(model));

    }

    @Test
    public void replace() {
        ToDoModel original = repo.all().get(1);
        ToDoModel edited = original.toBuilder()
                .isCompleted(true)
                .description("Currently on Tutorial #15")
                .build();
        repo.replace(edited);

        assertEquals(3, repo.all().size());
        assertSame(edited, repo.all().get(1));
    }
}
