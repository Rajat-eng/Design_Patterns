package com.task.State;

import com.task.Enums.TaskStatus;
import com.task.Models.Task;

public interface  TaskState {
    void startProgress(Task task);
    void completeTask(Task task);
    void reopenTask(Task task);
    TaskStatus getStatus();
}
