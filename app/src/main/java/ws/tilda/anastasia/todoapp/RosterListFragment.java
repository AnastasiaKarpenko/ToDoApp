package ws.tilda.anastasia.todoapp;

import android.app.Activity;
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
    private View mEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.todo_roster, container, false);

        mRecyclerView = result.findViewById(R.id.items);
        mEmpty = result.findViewById(R.id.empty);

        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            DividerItemDecoration decoration = new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(decoration);
        }

        mRecyclerView.setAdapter(new RosterListAdapter(this));

        if (mRecyclerView.getAdapter().getItemCount() > 0) {
            mEmpty.setVisibility(View.GONE);
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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

    interface Contract {
        void showModel(ToDoModel model);

        void addModel();
    }
}
