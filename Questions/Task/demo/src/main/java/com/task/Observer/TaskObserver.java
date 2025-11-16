package com.task.Observer;

import com.task.Models.Task;
import com.task.Models.User;

public interface TaskObserver{
    void update(Task task, String changeType);
    void update(User changedBy, Task task, String changeType);
}