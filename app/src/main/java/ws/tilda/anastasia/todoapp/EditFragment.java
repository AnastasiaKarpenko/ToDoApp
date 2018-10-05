package ws.tilda.anastasia.todoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.todoapp.databinding.TodoEditBinding;

public class EditFragment extends Fragment {
    private static final String TAG = EditFragment.class.getSimpleName();
    public static final String ARG_ID = "id";
    private TodoEditBinding mBinding;

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
        Bundle args = getArguments();
        String id = null;
        if (args == null || args.isEmpty()) {
            Log.e(TAG, "There are no arguments");
        } else {
            id = args.getString(ARG_ID);
        }

        return id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = TodoEditBinding.inflate(getLayoutInflater(), container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBinding.setModel(ToDoRepository.get().find(getModelId()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_edit, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        ToDoModel newModel = mBinding.getModel().toBuilder()
                .description(mBinding.desc.getText().toString())
                .notes(mBinding.notes.getText().toString())
                .isCompleted(mBinding.isCompleted.isChecked())
                .build();

        ToDoRepository.get().replace(newModel);


        Contract activity = (Contract) getActivity();
        if (activity != null) {
            (activity).finishEdit();
        }

    }

    interface Contract {
        void finishEdit();
    }
}
