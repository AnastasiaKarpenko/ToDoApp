package ws.tilda.anastasia.todoapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class RepoTests {
    private ToDoRepository repo;

    @Before
    public void setUp() {
        repo = ToDoRepository.get();
    }

    @Test
    public void getAll() {
        assertEquals(3, repo.all().size());
    }
}
