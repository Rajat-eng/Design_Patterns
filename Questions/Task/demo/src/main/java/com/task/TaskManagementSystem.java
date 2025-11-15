package com.task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.task.Enums.TaskPriority;
import com.task.Models.Task;
import com.task.Models.TaskList;
import com.task.Models.User;
import com.task.Strategy.TaskSortStrategy;

public class TaskManagementSystem {
    private static TaskManagementSystem instance;
    private Map<String, User> users;
    private Map<String, Task>  tasks;
    private Map<String, TaskList> taskLists;

    public static synchronized TaskManagementSystem getInstance() {
        if (instance == null) {
            instance = new TaskManagementSystem();
        }
        return instance;
    }

    public User createUser(String name, String email) {
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    // Additional methods for task and task list management can be added here
    public TaskList createTaskList(String listName) {
        TaskList taskList = new TaskList(listName);
        taskLists.put(taskList.getId(), taskList);
        return taskList;
    }

    public Task CreateTask(TaskList taskList,String title, String description, String createdByUserId,TaskPriority priority,LocalDate dueDate) {
        User createdBy = users.get(createdByUserId);
        Task task = new Task.Builder(title)
                .description(description)
                .dueDate(dueDate)
                .priority(priority)
                .createdBy(createdBy)
                .assignee(createdBy)
                .build();

        tasks.put(task.getId(), task);
        taskList.addTask(task);
        task.addObserver(createdBy);
        return task;
    }

    public List<Task> getTasksByUser(String userId) {
        User user = users.get(userId);
        return tasks.values().stream().filter(t -> t.getAssignee().equals(user)).toList();
    }

    public void deleteTask(TaskList taskList,String taskId) {
        // remove from taskList
        taskList.getTasks().removeIf(t -> t.getId().equals(taskId));
        tasks.remove(taskId);
    }

    public void updateTaskPriority(String taskId, TaskPriority newPriority) {
        Task task = tasks.get(taskId);
        task.updatePriority(newPriority);
    }

    public void assignTask(String taskId, String assigneeUserId) {
        Task task = tasks.get(taskId);
        User user = users.get(assigneeUserId);
        task.setAssignee(user);
    }

    public List<Task> searchTask(String keyword, TaskSortStrategy sortStrategy) {
        List<Task> result = tasks.values().stream()
                .filter(t -> t.getTitle().contains(keyword) || t.getDescription().contains(keyword))
                .toList();

        sortStrategy.sort(result);
        return result;
    }
}
