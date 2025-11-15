package com.task.Strategy;

import java.util.List;

public class SortByTaskPriority implements TaskSortStrategy {
    @Override
    public void sort(List<com.task.Models.Task> tasks) {
        tasks.sort((t1, t2) -> t1.getPriority().compareTo(t2.getPriority()));
    }
}
