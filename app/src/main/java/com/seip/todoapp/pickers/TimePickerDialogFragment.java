package com.seip.todoapp.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.seip.todoapp.utils.TodoConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        final  int hour = c.get(Calendar.HOUR_OF_DAY);
        final  int minute = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(), this,
                hour, minute, false);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        final Calendar calendar = Calendar.getInstance();
        calendar.set(0,0,0, hourOfDay, minute);
        final String selectedTime = sdf.format(calendar.getTime());
        final Bundle bundle = new Bundle();
        bundle.putString(TodoConstants.TIME_KEY, selectedTime);
        bundle.putInt(TodoConstants.HOUR, hourOfDay);
        bundle.putInt(TodoConstants.MINUTE, minute);
        getParentFragmentManager()
                .setFragmentResult(TodoConstants.REQUEST_KEY, bundle);

    }
}