package com.task.Strategy;

import java.util.List;

import com.task.Models.Task;

public class SortByDueDate implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
    }
    
}
