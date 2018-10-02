package ws.tilda.anastasia.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;


public class RosterListAdapter extends RecyclerView.Adapter<RosterRowHolder> {
    final private List<ToDoModel> models;

    RosterListAdapter(RosterListFragment host) {
        models = ToDoRepository.get().all();
    }

    @NonNull
    @Override
    public RosterRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RosterRowHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
