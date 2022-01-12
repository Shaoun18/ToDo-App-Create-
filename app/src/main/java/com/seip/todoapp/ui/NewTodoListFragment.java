package com.seip.todoapp.ui;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.seip.todoapp.databinding.FragmentNewTodoListBinding;
import com.seip.todoapp.entities.Todo;
import com.seip.todoapp.pickers.DatePickerDialogFragment;
import com.seip.todoapp.pickers.TimePickerDialogFragment;
import com.seip.todoapp.todoworkmanager.TodoWorker;
import com.seip.todoapp.utils.TodoConstants;
import com.seip.todoapp.viewmodels.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class NewTodoListFragment extends Fragment {
    private FragmentNewTodoListBinding binding;
    String dateString, timeString;
    int year, month, day, hour, minute;
    private String priority = TodoConstants.NORMAL;
    private TodoViewModel todoViewModel;

    public NewTodoListFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewTodoListBinding.inflate(inflater, container, false);
        todoViewModel = new ViewModelProvider(requireActivity())
                .get(TodoViewModel.class);
        initDateAndTime();
        binding.priorityRG.setOnCheckedChangeListener((group, checkId) -> {
            final RadioButton rb = group.findViewById(checkId);
            priority = rb.getText().toString();
        });
        binding.dateBtn.setOnClickListener(v->{
            new DatePickerDialogFragment()
                    .show(getChildFragmentManager(), null);

        });
        binding.timeBtn.setOnClickListener(view -> {
            new TimePickerDialogFragment()
                    .show(getChildFragmentManager(), null);
        });
        binding.saveBtn.setOnClickListener(view -> {
            final String name = binding.todoInputET.getText().toString();
            final Todo todo = new Todo(name, priority, dateString, timeString, false);
            todoViewModel.insertTodo(todo);
            binding.todoInputET.setText("");
            if(priority.equals(TodoConstants.HIGH)){
                scheduleNotification(name);
            }

        });

        getChildFragmentManager().setFragmentResultListener(
                TodoConstants.REQUEST_KEY, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (result.containsKey(TodoConstants.DATE_KEY)){
                            dateString = result.getString(TodoConstants.DATE_KEY);
                            year = result.getInt(TodoConstants.YEAR);
                            month = result.getInt(TodoConstants.MONTH);
                            day = result.getInt(TodoConstants.DAY);
                            binding.dateBtn.setText(dateString);
                        }
                        else if (result.containsKey(TodoConstants.TIME_KEY)){
                            timeString = result.getString(TodoConstants.TIME_KEY);
                            hour = result.getInt(TodoConstants.HOUR);
                            minute = result.getInt(TodoConstants.MINUTE);
                            binding.timeBtn.setText(timeString);

                        }
                    }
                });

        return binding.getRoot();
    }

    private void scheduleNotification(String name) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(year,month,day,hour,minute);
        final  Date currentDate  = new Date();
        long diff = calendar.getTimeInMillis() - currentDate.getTime();
        //Log.e("todo Worker", "schedualnotification"+(diff/(1000 * 60)));

        final OneTimeWorkRequest request  = new OneTimeWorkRequest.Builder(TodoWorker.class).
                setInitialDelay(diff, TimeUnit.MILLISECONDS).
                setInputData(new Data.Builder().putString("name", name).build()).build();
        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void initDateAndTime(){
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);
        day = calendar.get(calendar.DAY_OF_MONTH);
        hour = calendar.get(calendar.HOUR);
        minute = calendar.get(calendar.MINUTE);
        dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        timeString = new SimpleDateFormat("hh:mm a").format(new Date());
        //binding.dateBtn.setText(dateString);
        //binding.timeBtn.setText(timeString);
    }
}