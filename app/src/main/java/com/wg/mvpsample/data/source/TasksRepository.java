package com.wg.mvpsample.data.source;

import com.wg.mvpsample.data.TaskBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Wangg
 * @Name：TasksRepository
 * @Description:操作数据库的方法
 * @Created on:2017/2/28  20:19.
 */

public class TasksRepository implements TasksDataSource {

    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;

    private final TasksDataSource mTasksLocalDataSource;

    Map<String, TaskBean> mCachedTasks;


    boolean mCacheIsDirty = false;

    public TasksRepository(TasksDataSource mTasksRemoteDataSource, TasksDataSource mTasksLocalDataSource) {
        this.mTasksRemoteDataSource = mTasksRemoteDataSource;
        this.mTasksLocalDataSource = mTasksLocalDataSource;
    }

    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource,
                                              TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getTasks(final LoadTasksCallback callback) {

        if (callback != null) {
            if (mCachedTasks != null && !mCacheIsDirty) {
                callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
                return;
            }

            if (mCacheIsDirty) {
                // If the cache is dirty we need to fetch new data from the network.
                getTasksFromRemoteDataSource(callback);
            } else {
                // Query the local storage if available. If not, query the network.
                mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
                    @Override
                    public void onTasksLoaded(List<TaskBean> tasks) {
                        refreshCache(tasks);
                        callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
                    }

                });
            }
        }

    }

    @Override
    public void getTask(final String taskId, final GetTaskCallback callback) {

        if (taskId != null && callback != null) {

            TaskBean cachedTask = getTaskWithId(taskId);

            // Respond immediately with cache if available
            if (cachedTask != null) {
                callback.onTaskLoaded(cachedTask);
                return;
            }

            // Load from server/persisted if needed.

            // Is the task in the local data source? If not, query the network.
            mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
                @Override
                public void onTaskLoaded(TaskBean task) {
                    // Do in memory cache update to keep the app UI up to date
                    if (mCachedTasks == null) {
                        mCachedTasks = new LinkedHashMap<>();
                    }
                    mCachedTasks.put(task.getmId(), task);
                    callback.onTaskLoaded(task);
                }
            });
        }
    }


    @Override
    public void saveTask(TaskBean taskBean) {
        if (taskBean != null) {
            mTasksRemoteDataSource.saveTask(taskBean);
            mTasksLocalDataSource.saveTask(taskBean);

            // Do in memory cache update to keep the app UI up to date
            if (mCachedTasks == null) {
                mCachedTasks = new LinkedHashMap<>();
            }
            mCachedTasks.put(taskBean.getmId(), taskBean);
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;

    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }

    @Override
    public void deleteTask(String taskId) {
        if (taskId != null) {
            mTasksRemoteDataSource.deleteTask(taskId);
            mTasksLocalDataSource.deleteTask(taskId);
        }
        mCachedTasks.remove(taskId);
    }


    private void getTasksFromRemoteDataSource(final LoadTasksCallback callback) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<TaskBean> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
            }
        });
    }

    private void refreshCache(List<TaskBean> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (TaskBean task : tasks) {
            mCachedTasks.put(task.getmId(), task);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<TaskBean> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (TaskBean task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private TaskBean getTaskWithId(String id) {
        if (id != null) {
            if (mCachedTasks == null || mCachedTasks.isEmpty()) {
                return null;
            } else {
                return mCachedTasks.get(id);
            }

        }
        return null;
    }
}

