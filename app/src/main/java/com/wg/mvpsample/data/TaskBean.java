package com.wg.mvpsample.data;

import java.util.UUID;

/**
 * @author: Wangg
 * @Name：TaskBean
 * @Description: 数据model
 * @Created on:2017/2/28  19:42.
 */

public final class TaskBean {

    private final String mId;

    private final String mTitle;

    private final String mDescription;

    public TaskBean(String mTitle, String mDescription, String mId) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public TaskBean(String mTitle, String mDescription) {

        this(mTitle, mDescription, UUID.randomUUID().toString());
    }

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }


    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
