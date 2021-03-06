package ws.tilda.anastasia.todoapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class RosterListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mEmpty, mProgress;
    private RosterViewModel mViewModel;
    private RosterListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.todo_roster, container, false);

        mRecyclerView = result.findViewById(R.id.items);
        mEmpty = result.findViewById(R.id.empty);
        mProgress = result.findViewById(R.id.progressBar);

        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(decoration);
        }

        mAdapter = new RosterListAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

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
        inflater.inflate(R.menu.actions_roster, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Contract activity = (Contract) getActivity();
        if (item.getItemId() == R.id.add) {
            if (activity != null) {
                activity.addModel();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void replace(ToDoModel model) {
        mViewModel.process(Action.edit(model));
    }

    interface Contract {
        void showModel(ToDoModel model);

        void addModel();
    }

    public void render(ViewState state) {
        mAdapter.setState(state);

        if (state.isLoaded()) {
            mProgress.setVisibility(View.GONE);

            if (mRecyclerView.getAdapter().getItemCount() > 0) {
                mEmpty.setVisibility(View.GONE);
            } else {
                mEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            mProgress.setVisibility(View.VISIBLE);
            mEmpty.setVisibility(View.GONE);
        }
    }

    void showModel(ToDoModel model) {
        Contract activity = (Contract) getActivity();
        if (activity != null) {
            activity.showModel(model);
        }
        mViewModel.process(Action.show(model));
    }
}
