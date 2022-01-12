package com.seip.todoapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seip.todoapp.R;
import com.seip.todoapp.adapter.Todoadapter;
import com.seip.todoapp.databinding.FragmentDoneTodoListBinding;
import com.seip.todoapp.entities.Todo;
import com.seip.todoapp.listener.OnTodoStatusChangeListener;
import com.seip.todoapp.viewmodels.TodoViewModel;

public class DoneTodoListFragment extends Fragment implements OnTodoStatusChangeListener {
    private FragmentDoneTodoListBinding binding;
    private TodoViewModel viewModel;
    public DoneTodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = FragmentDoneTodoListBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        final Todoadapter adapter = new Todoadapter(this);
        binding.DoneTodolistRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.DoneTodolistRV.setAdapter(adapter);
        viewModel.getAllDonetodos().observe(getViewLifecycleOwner(), todoList -> {
            adapter.submitList(todoList);
            if(todoList.isEmpty()){
                binding.emptyListMSGRV.setVisibility(View.VISIBLE);
            }else {
                binding.emptyListMSGRV.setVisibility(View.GONE);
            }
            Toast.makeText(getActivity(), ""+todoList.size(), Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void OnTodoStatusChangeListener(Todo todo) {
        viewModel.updateTodo(todo);
    }
}