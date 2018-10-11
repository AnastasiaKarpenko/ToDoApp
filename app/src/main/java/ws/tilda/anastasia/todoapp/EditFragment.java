package ws.tilda.anastasia.todoapp;

import android.arch.lifecycle.ViewModelProviders;
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
    private RosterViewModel mViewModel;
    private MenuItem mDeleteMenu;

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
        super.onViewCreated(view, savedInstanceState);
        mViewModel.stateStream().observe(this, this::render);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(RosterViewModel.class);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_edit, menu);
        mDeleteMenu = menu.findItem(R.id.delete);
        mDeleteMenu.setVisible(getModelId() != null);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            save();
            return true;
        } else if (item.getItemId() == R.id.delete) {
            delete();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        ToDoModel.Builder builder;

        if (mBinding.getModel() == null) {
            builder = ToDoModel.creator();
        } else {
            builder = mBinding.getModel().toBuilder();
        }


        ToDoModel newModel = builder
                .description(mBinding.desc.getText().toString())
                .notes(mBinding.notes.getText().toString())
                .isCompleted(mBinding.isCompleted.isChecked())
                .build();

        if (mBinding.getModel() == null) {
            mViewModel.process(Action.add(newModel));
        } else {
            mViewModel.process(Action.edit(newModel));
        }

        finishEditWithNullCheck(false);

    }

    private void finishEditWithNullCheck(boolean deleted) {
        Contract activity = (Contract) getActivity();
        if (activity != null) {
            (activity).finishEdit(deleted);
        }
    }

    public void delete() {
        mViewModel.process(Action.delete(mBinding.getModel()));
        finishEditWithNullCheck(true);

    }

    private void render(ViewState state) {
        if (state != null) {
            if (getModelId() == null) {
                if (mDeleteMenu != null) {
                    mDeleteMenu.setVisible(false);
                }
            } else {
                ToDoModel model = state.current();

                mBinding.setModel(model);
            }
        }
    }

    interface Contract {
        void finishEdit(boolean deleted);
    }
}
