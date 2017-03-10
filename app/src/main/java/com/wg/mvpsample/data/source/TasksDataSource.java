package com.wg.mvpsample.data.source;

import com.wg.mvpsample.data.TaskBean;

import java.util.List;

/**
 * @author: Wangg
 * @Name：TasksDataSource
 * @Description:
 * @Created on:2017/2/28  19:55.
 */

public interface TasksDataSource {

    interface LoadTasksCallback {
        void onTasksLoaded(List<TaskBean> taskBeens);
    }

    interface GetTaskCallback {
        void onTaskLoaded(TaskBean taskBean);
    }

    void getTasks(LoadTasksCallback callback);

    void getTask(String taskId, GetTaskCallback callback);

    void saveTask(TaskBean taskBean);

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(String taskId);

}
