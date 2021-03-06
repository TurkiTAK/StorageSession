package com.turki.storageday;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void save(Task task);
    @Delete
    void delete(Task task);
    @Update
    void update(Task task);

    @Query("SELECT*FROM task")
    List<Task> getAllTasks();

}
