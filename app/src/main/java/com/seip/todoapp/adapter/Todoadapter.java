package com.seip.todoapp.adapter;

import android.media.browse.MediaBrowser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.seip.todoapp.databinding.TodoRowBinding;
import com.seip.todoapp.entities.Todo;
import com.seip.todoapp.listener.OnTodoStatusChangeListener;

import java.util.ArrayList;
import java.util.List;

public class Todoadapter extends ListAdapter<Todo, Todoadapter.TodoViewHolder> {

private OnTodoStatusChangeListener listener;

    public Todoadapter(Fragment fragment){
        super(new TodoDiff());
        listener = (OnTodoStatusChangeListener) fragment;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final TodoRowBinding binding = TodoRowBinding.inflate(LayoutInflater
                .from(parent.getContext()),parent,false);

        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final Todo todo = getItem(position);
        holder.bind(todo);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{
private TodoRowBinding binding;
public void bind(Todo todo){
    binding.setTodo(todo);
}
        public TodoViewHolder(TodoRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.rowTodoComleteBtn.setOnClickListener(v ->{
                final int position = getAdapterPosition();
                final Todo  todo =getItem(position);
                todo.setCompleted(!todo.getCompleted());
                listener.OnTodoStatusChangeListener(todo);
            });
        }
    }
    public static class TodoDiff extends DiffUtil.ItemCallback<Todo>{

        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();        }
    }
}
