package com.task.wifitask.Base;

import androidx.annotation.NonNull;

import java.util.List;

public interface RepositoryManipulation<Entity> {
    public void cleverInsert(@NonNull Entity entity);

    public void insert(@NonNull Entity entity);

    public void update(@NonNull Entity entity);

    public boolean contains(@NonNull Entity entity);

    public List<Entity> toCurrentList();

    public List<Entity> toList();

}
