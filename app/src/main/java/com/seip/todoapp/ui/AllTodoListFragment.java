package com.seip.todoapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seip.todoapp.R;
import com.seip.todoapp.adapter.Todoadapter;
import com.seip.todoapp.databinding.FragmentAllTodoListBinding;
import com.seip.todoapp.entities.Todo;
import com.seip.todoapp.listener.OnTodoStatusChangeListener;
import com.seip.todoapp.viewmodels.TodoViewModel;

public class AllTodoListFragment extends Fragment implements OnTodoStatusChangeListener {
    private FragmentAllTodoListBinding binding;
    private TodoViewModel todoViewModel;

    public AllTodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllTodoListBinding.inflate(inflater,container, false);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        final Todoadapter adapter = new Todoadapter(this);
        binding.alltodoRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.alltodoRV.setAdapter(adapter);
        todoViewModel.getAlltodos().observe(getViewLifecycleOwner(), todoList -> {
            adapter.submitList(todoList);
            Toast.makeText(getActivity(), ""+todoList.size(), Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void OnTodoStatusChangeListener(Todo todo) {
        todoViewModel.updateTodo(todo);
    }
}