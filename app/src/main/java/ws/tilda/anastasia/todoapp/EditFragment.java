package ws.tilda.anastasia.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EditFragment extends Fragment {
    public static final String ARG_ID = "id";

    static EditFragment newInstance(ToDoModel model) {
        EditFragment result = new EditFragment();

        if (model != null) {
            Bundle args = new Bundle();

            args.putString(ARG_ID, model.id());
            result.setArguments(args);
        }

        return result;
    }

    public String getModelId() {
        return getArguments().getString(ARG_ID);
    }
}
