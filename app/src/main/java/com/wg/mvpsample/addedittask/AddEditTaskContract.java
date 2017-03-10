package com.wg.mvpsample.addedittask;

import com.wg.mvpsample.BasePresenter;
import com.wg.mvpsample.BaseView;
import com.wg.mvpsample.data.Person;

/**
 * @author: Wangg
 * @Name：AddEditTaskContract
 * @Description:
 * @Created on:2017/3/10  10:02.
 */

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {
        void postViewData(Person p);
    }

    interface Presenter extends BasePresenter {
        void addViewData(Person p);
    }
}

