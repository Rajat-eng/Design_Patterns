package com.task.Strategy;

import java.util.List;

import com.task.Models.Task;

public interface  TaskSortStrategy {
    void sort(List<Task> tasks);
}
