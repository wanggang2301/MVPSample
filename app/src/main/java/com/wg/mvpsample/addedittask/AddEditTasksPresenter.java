package com.wg.mvpsample.addedittask;

import com.wg.mvpsample.data.Person;

/**
 * @author: Wangg
 * @Name：AddEditTasksPresenter
 * @Description:
 * @Created on:2017/3/10  10:26.
 */

public class AddEditTasksPresenter implements AddEditTaskContract.Presenter {


    private AddEditTaskContract.View addEditView;


    public AddEditTasksPresenter(AddEditTaskContract.View addEditView) {
        this.addEditView = addEditView;
        addEditView.setPresenter(this);
    }

    @Override
    public void addViewData(Person p) {
        addEditView.postViewData(p);
    }
}
