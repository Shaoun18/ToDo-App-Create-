package com.seip.todoapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.seip.todoapp.entities.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void insertTodo(Todo todo);

    @Update
    void updateTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Query("select * from tbl_todo order by id desc")
    LiveData<List<Todo>> getAllTodos();
    @Query("select * from tbl_todo where completed = :status order by id desc")
    LiveData<List<Todo>> getAllDoneTodos(boolean status);
}
