/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wg.mvpsample.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.wg.mvpsample.data.TaskBean;
import com.wg.mvpsample.data.source.TasksDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TasksRemoteDataSource implements TasksDataSource {

    private static TasksRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, TaskBean> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    public static TasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TasksRemoteDataSource() {
    }

    private static void addTask(String title, String description) {
        TaskBean newTask = new TaskBean(title, description);
        TASKS_SERVICE_DATA.put(newTask.getmId(), newTask);
    }

    /**
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getTasks(final LoadTasksCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                List<TaskBean> list = new ArrayList<TaskBean>();

                for (TaskBean taskBean : TASKS_SERVICE_DATA.values()) {
                    list.add(taskBean);
                }
                callback.onTasksLoaded(list);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    /**
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getTask(String taskId, final GetTaskCallback callback) {
        final TaskBean task = TASKS_SERVICE_DATA.get(taskId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTaskLoaded(task);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveTask(TaskBean task) {
        TASKS_SERVICE_DATA.put(task.getmId(), task);
    }


    @Override
    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllTasks() {
        TASKS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        TASKS_SERVICE_DATA.remove(taskId);
    }
}