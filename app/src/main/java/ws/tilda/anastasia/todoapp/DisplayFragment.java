package ws.tilda.anastasia.todoapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.todoapp.databinding.TodoDisplayBinding;

public class DisplayFragment extends Fragment {
    public static final String ARG_ID = "id";
    private RosterViewModel mViewModel;

    private TodoDisplayBinding mBinding;

    public static DisplayFragment newInstance(ToDoModel model) {
        DisplayFragment result = new DisplayFragment();

        if (model != null) {
            Bundle args = new Bundle();
            args.putString(ARG_ID, model.id());
            result.setArguments(args);
        }
        return result;
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
        inflater.inflate(R.menu.actions_display, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {


            Contract activity = (Contract) getActivity();
            if (activity != null) {
                activity.editModel(mBinding.getModel());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = TodoDisplayBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    private String getModelId() {

        Bundle arguments = getArguments();
        String string = null;
        if (arguments != null) {
            string = arguments.getString(ARG_ID);
        }

        return string;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.stateStream()
                .observe(this, this::render);
    }

    private void render(ViewState state) {
        if (state != null) {
            ToDoModel model = state.current();

            if (model != null) {
                mBinding.setModel(model);
                mBinding.setCreatedOn(DateUtils.getRelativeDateTimeString(getActivity(), model.createdOn().getTimeInMillis(),
                        DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
            }
        }
    }

    interface Contract {
        void editModel(ToDoModel model);
    }
}
