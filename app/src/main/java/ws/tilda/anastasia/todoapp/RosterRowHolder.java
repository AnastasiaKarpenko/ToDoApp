package ws.tilda.anastasia.todoapp;

import android.support.v7.widget.RecyclerView;

import ws.tilda.anastasia.todoapp.databinding.TodoRowBinding;


public class RosterRowHolder extends RecyclerView.ViewHolder {
    final private TodoRowBinding mBinding;

    public RosterRowHolder(TodoRowBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    void bind(ToDoModel model) {
        mBinding.setModel(model);
        mBinding.setHolder(this);
        mBinding.executePendingBindings();
    }
}
