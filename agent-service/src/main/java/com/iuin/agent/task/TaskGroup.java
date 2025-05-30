package com.iuin.agent.task;

import java.util.List;

public class TaskGroup {
    public boolean parallel;
    public List<Task> tasks;
    public TaskGroup(boolean parallel, List<Task> tasks) {
        this.parallel = parallel;
        this.tasks = tasks;
    }
} 