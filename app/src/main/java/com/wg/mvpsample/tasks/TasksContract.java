package com.wg.mvpsample.tasks;

import com.wg.mvpsample.BasePresenter;
import com.wg.mvpsample.BaseView;

/**
 * @author: Wangg
 * @Name：TasksContract
 * @Description: xContract作为合同接口，统一管理View和Presenter的接口，便于查看和维护View和Presenter的功能
 * @Created on:2017/2/28  19:38.
 */

public interface TasksContract {

    interface View extends BaseView<Presenter> {
        //显示的View
        void showAddTask();

        void updataAdapter();

        void showToastView(String msg);

        void showNoTaskView();

        void showTaskView();

    }

    //控制View的显示
    interface Presenter extends BasePresenter {
        void addNewTask();

        void addPersonTask();

        void showToast(String msg);

        void showNoTask();

        void showTask();
    }
}
