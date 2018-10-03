package ws.tilda.anastasia.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ws.tilda.anastasia.todoapp.databinding.TodoRowBinding;


public class RosterListAdapter extends RecyclerView.Adapter<RosterRowHolder> {
    final private List<ToDoModel> mModels;
    final private RosterListFragment mHost;
    final private ToDoRepository mRepository;

    RosterListAdapter(RosterListFragment host) {
        mRepository = ToDoRepository.get();
        mModels = mRepository.all();
        mHost = host;
    }

    @NonNull
    @Override
    public RosterRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoRowBinding binding =
                TodoRowBinding.inflate(mHost.getLayoutInflater(), parent, false);
        return new RosterRowHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RosterRowHolder holder, int position) {
        holder.bind(mModels.get(position));

    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void replace(ToDoModel model, boolean isChecked) {
        mRepository.replace(model.toBuilder().isCompleted(isChecked).build());
    }

    void showModel(ToDoModel model) {
        ((RosterListFragment.Contract) mHost.getActivity()).showModel(model);
    }
}
