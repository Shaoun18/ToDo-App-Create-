package com.seip.todoapp.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.seip.todoapp.utils.TodoConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,
                year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dauOfMonth) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dauOfMonth);
        final String selectedDate = sdf.format(calendar.getTime());
        final Bundle bundle = new Bundle();
        bundle.putString(TodoConstants.DATE_KEY, selectedDate);
        bundle.putInt(TodoConstants.YEAR, year);
        bundle.putInt(TodoConstants.MONTH, month);
        bundle.putInt(TodoConstants.DAY, dauOfMonth);
        getParentFragmentManager()
                .setFragmentResult(TodoConstants.REQUEST_KEY, bundle);

    }
}
