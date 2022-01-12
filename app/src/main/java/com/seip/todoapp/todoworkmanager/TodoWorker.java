package com.seip.todoapp.todoworkmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.seip.todoapp.R;

public class TodoWorker extends Worker {
    private Context context;
    public TodoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        final String name = getInputData().getString("name");
        sendNotification(name);
        return Result.success();
    }

    private void sendNotification(String name) {
        final String CHANNEL_ID = "my channel";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID).
                setSmallIcon(R.drawable.ic_baseline_work_24).setContentTitle("Todo Alert!").
                setContentText("it is time to do "+ name).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String description = "todo Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Todo",importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify((int)System.currentTimeMillis(),builder.build());
    }
}
