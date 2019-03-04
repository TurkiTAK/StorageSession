package com.turki.storageday;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase{
    public abstract TaskDAO getTaskDao();

}
