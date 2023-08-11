package com.task.wifitask.Base;

import androidx.annotation.NonNull;

import java.util.List;

public interface RepositoryManipulation<Entity> {
    void cleverInsert(@NonNull Entity entity);

    void insert(@NonNull Entity entity);

    void update(@NonNull Entity entity);

    boolean contains(@NonNull Entity entity);

    List<Entity> toCurrentList();

    List<Entity> toList();

}
