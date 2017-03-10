package com.wg.mvpsample.data.source.local;

import android.provider.BaseColumns;

/**
 * @author: Wangg
 * @Name：TasksPersistenceContract
 * @Description: 数据库字段  表名
 * @Created on:2017/2/28  19:57.
 */

public class TasksPersistenceContract {
    public TasksPersistenceContract() {
    }

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "wanggang";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }


}
