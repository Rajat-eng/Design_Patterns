package com.task.State;

import com.task.Enums.TaskStatus;
import com.task.Models.Task;

public class TodoState implements TaskState {
    @Override
    public void startProgress(Task task) {
        task.setState(new InProgressState());
        task.addLog("Task moved to In Progress");
    }

    @Override
    public void completeTask(Task task) {
        throw new IllegalStateException("Cannot complete a task that is in To Do state.");
    }

    @Override
    public void reopenTask(Task task) {
        throw new IllegalStateException("Task is already in To Do state.");
    }

    @Override
    public TaskStatus getStatus() {
        return TaskStatus.TODO;
    }
    
}
