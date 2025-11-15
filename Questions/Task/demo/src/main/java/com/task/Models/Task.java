package com.task.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.task.Enums.TaskPriority;
import com.task.Enums.TaskStatus;
import com.task.Observer.TaskObserver;
import com.task.State.TaskState;
import com.task.State.TodoState;

public abstract class Task{

    protected String id;
    protected String title;
    protected String description;
    protected LocalDate dueDate;
    protected TaskPriority priority;
    protected User createdBy;
    protected User assignee;

    protected TaskState currentState;
    protected Set<Tag> tags;
    protected List<Comment> comments;
    protected List<Task> subtasks;
    protected List<ActivityLog> activityLogs;
    protected List<TaskObserver> observers;

    // -------- Abstract Getters --------
    public abstract String getId();
    public abstract String getTitle();
    public abstract String getDescription();
    public abstract LocalDate getDueDate();
    public abstract TaskPriority getPriority();
    public abstract User getAssignee();
    public abstract User getCreatedBy();
    public abstract TaskStatus getStatus();

    public static class BaseTask extends Task {

        public BaseTask(
                String id,
                String title,
                String description,
                LocalDate dueDate,
                TaskPriority priority,
                User createdBy,
                User assignee,
                Set<Tag> tags
        ) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.createdBy = createdBy;
            this.assignee = assignee;

            this.tags = tags != null ? tags : new HashSet<>();

            this.currentState = new TodoState();
            this.comments = new ArrayList<>();
            this.subtasks = new ArrayList<>();
            this.activityLogs = new ArrayList<>();
            this.observers = new ArrayList<>();

            addLog("Task created with title: " + title);
        }

        // Concrete Getters
        @Override public String getId() { return this.id; }
        @Override public String getTitle() { return this.title; }
        @Override public String getDescription() { return this.description; }
        @Override public LocalDate getDueDate() { return this.dueDate; }
        @Override public TaskPriority getPriority() { return this.priority; }
        @Override public User getAssignee() { return this.assignee; }
        @Override public User getCreatedBy() { return this.createdBy; }
        @Override public TaskStatus getStatus() { return this.currentState.getStatus(); }
    }

    public static class Builder {

        private String title;
        private String description = "";
        private LocalDate dueDate;
        private TaskPriority priority = TaskPriority.MEDIUM;
        private User createdBy;
        private User assignee;
        private Set<Tag> tags = new HashSet<>();

        public Builder(String title) {
            this.title = title;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder priority(TaskPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder createdBy(User user) {
            this.createdBy = user;
            return this;
        }

        public Builder assignee(User user) {
            this.assignee = user;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Task build() {
            String id = UUID.randomUUID().toString();

            return new BaseTask(
                    id,
                    title,
                    description,
                    dueDate,
                    priority,
                    createdBy,
                    assignee,
                    tags
            );
        }
    }

    public void addLog(String log) {
        if (activityLogs == null) activityLogs = new ArrayList<>();
        activityLogs.add(new ActivityLog(log));
    }

    public void addObserver(TaskObserver obs) {
        if (observers == null) observers = new ArrayList<>();
        observers.add(obs);
    }

    public void notifyObservers(String changeType) {
        if (observers == null) return;
        for (TaskObserver obs : observers) obs.update(this, changeType);
    }

    public void updatePriority(TaskPriority priority) {
        this.priority = priority;
        notifyObservers("priority");
    }

    public void setAssignee(User user) {
        this.assignee = user;
        addObserver(user);
        addLog("Assignee changed to " + user.getName());
        notifyObservers("assignee");
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        addLog("Comment added");
        notifyObservers("comment");
    }

    public void addSubtask(Task subtask,User ChangedBy) {
        subtasks.add(subtask);
        addLog("Subtask added");
        notifyObservers("subtask");
    }

    public void setState(TaskState state) {
        this.currentState = state;
    }

    public boolean isComposite() { return !subtasks.isEmpty(); }

    public void display(String indent) {
        System.out.println(indent + "- " + title + " [" + getStatus() + ", " + priority + ", Due: " + dueDate + "]");
        if (isComposite()) {
            for (Task subtask : subtasks) {
                subtask.display(indent + "  ");
            }
        }
    }
}
    
