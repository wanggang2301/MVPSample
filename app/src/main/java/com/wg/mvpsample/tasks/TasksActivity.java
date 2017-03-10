package com.wg.mvpsample.tasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wg.mvpsample.R;
import com.wg.mvpsample.util.ActivityUtils;

/**
 * 首页Activity
 *
 * @des：主要负责创建View和Presenter实例，并将它们联系起来
 */
public class TasksActivity extends AppCompatActivity {

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建View
        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        //创建Presenter实例，将Presenter与View联系起来
        mTasksPresenter = new TasksPresenter(tasksFragment);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("asd", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("asd", "onpase");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("asd", "onStop");

    }
}
