package com.seip.todoapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.seip.todoapp.daos.TodoDao;
import com.seip.todoapp.entities.Todo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1)
public abstract class TodoDb extends RoomDatabase {
    public abstract TodoDao getTodoDao();
    public static TodoDb db;
    public static final ExecutorService service = Executors.newFixedThreadPool(4);

    public static TodoDb getInstance(Context context){
        if (db == null){
            db = (TodoDb) Room.databaseBuilder(context, TodoDb.class, "todo_db")
                    .build();
            return db;
        }
        return db;
    }

}
