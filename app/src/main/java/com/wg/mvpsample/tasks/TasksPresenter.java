package com.wg.mvpsample.tasks;

/**
 * @author: Wangg
 * @Name：TasksPresenter
 * @Description:
 * @Created on:2017/2/28  17:57.
 */

public class TasksPresenter implements TasksContract.Presenter {

    // private final TasksRepository mTasksRepository;、

    private final TasksContract.View mTasksView;

   /* public TasksPresenter(TasksRepository tasksRepository, TasksContract.View tasksView) {
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }*/


    //Viw和Presenter联系起来
    public TasksPresenter(TasksContract.View tasksView) {
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }

    //增加新任务
    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }

    @Override
    public void addPersonTask() {
        mTasksView.updataAdapter();
    }


    @Override
    public void showToast(String msg) {
        mTasksView.showToastView(msg);
    }

    @Override
    public void showNoTask() {
        mTasksView.showNoTaskView();
    }

    @Override
    public void showTask() {
        mTasksView.showTaskView();
    }
}
