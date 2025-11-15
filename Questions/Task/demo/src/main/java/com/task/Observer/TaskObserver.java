package com.task.Observer;

import com.task.Models.Task;

public interface TaskObserver{
    void update(Task task, String changeType);
}