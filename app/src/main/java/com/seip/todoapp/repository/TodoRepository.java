package com.seip.todoapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.seip.todoapp.daos.TodoDao;
import com.seip.todoapp.db.TodoDb;
import com.seip.todoapp.entities.Todo;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    public TodoRepository(Context context){
        todoDao = TodoDb.getInstance(context).getTodoDao();
    }
    public void insertTodo(Todo todo){
        TodoDb.service.execute(()->{
            todoDao.insertTodo(todo);
        });
    }

    public void updateTodo(Todo todo){
        TodoDb.service.execute(()->{
            todoDao.updateTodo(todo);
        });
    }

    public void deleteTodo(Todo todo){
        TodoDb.service.execute(()->{
            todoDao.deleteTodo(todo);
        });
    }

    public LiveData<List<Todo>> getAlltodos(){
        return todoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getAllDonetodos(){
        return todoDao.getAllDoneTodos(true);
    }



}
