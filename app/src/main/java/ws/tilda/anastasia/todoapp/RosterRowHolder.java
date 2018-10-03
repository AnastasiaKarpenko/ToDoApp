package ws.tilda.anastasia.todoapp;

import android.support.v7.widget.RecyclerView;

import ws.tilda.anastasia.todoapp.databinding.TodoRowBinding;


public class RosterRowHolder extends RecyclerView.ViewHolder {
    final private TodoRowBinding mBinding;
    final private RosterListAdapter mAdapter;

    public RosterRowHolder(TodoRowBinding binding, RosterListAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
    }

    void bind(ToDoModel model) {
        mBinding.setModel(model);
        mBinding.setHolder(this);
        mBinding.executePendingBindings();
    }

    public void completeChanged(ToDoModel model, boolean isChecked) {
        if (model.isCompleted() != isChecked) {
            mAdapter.replace(model, isChecked);
        }
    }

    public void onClick() {
        mAdapter.showModel(mBinding.getModel());
    }


}
