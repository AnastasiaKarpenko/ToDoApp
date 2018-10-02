package ws.tilda.anastasia.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class DisplayFragment extends Fragment {
    public static final String ARG_ID = "id";

    public DisplayFragment newInstance(ToDoModel model) {
        DisplayFragment result = new DisplayFragment();

        if (model != null) {
            Bundle args = new Bundle();
            args.putString(ARG_ID, model.id());
            result.setArguments(args);
        }
        return result;
    }
}
