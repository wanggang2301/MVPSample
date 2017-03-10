package com.wg.mvpsample.tasks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wg.mvpsample.R;
import com.wg.mvpsample.addedittask.AddEditTaskActivity;
import com.wg.mvpsample.data.Person;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;
    private TasksAdapter mListAdapter;

    Button btn_add;

    private List<Person> list;

    public TasksFragment() {
        // Requires empty public constructor
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();

        list.add(new Person("好好", "89"));
        list.add(new Person("是看得见 ", "110"));
        mListAdapter = new TasksAdapter(list, taskItemListener);
    }


    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);
        EventBus.getDefault().register(this);

        btn_add = (Button) getActivity().findViewById(R.id.btn_add);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加新任务
                mPresenter.addNewTask();
            }
        });


        ListView listView = (ListView) root.findViewById(R.id.tasks_list);
        listView.setAdapter(mListAdapter);

        return root;
    }


    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void updataAdapter() {

        mListAdapter.replaceData(list);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    TaskItemListener taskItemListener = new TaskItemListener() {
        @Override
        public void onTaskClick(Person clickedTask) {
            mPresenter.showToast("数据==" + clickedTask.getName());

        }
    };

    @Override
    public void showToastView(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示AddEdit返回的数据
     */
    @Subscribe
    public void getPersonData(Person person) {
        Log.d("wangg", "收到的数据===" + person.getName() + "__" + person.getAge());
        list.add(person);

        mPresenter.addPersonTask();

    }

    private static class TasksAdapter extends BaseAdapter {

        private List<Person> mPersons;

        private TaskItemListener taskItemListener;

        public TasksAdapter(List<Person> tasks, TaskItemListener itemListener) {
            setList(tasks);
            taskItemListener = itemListener;
        }


        public void replaceData(List<Person> tasks) {
            setList(tasks);
            notifyDataSetChanged();
        }

        private void setList(List<Person> tasks) {
            if (tasks.size() > 0) {
                mPersons = tasks;

            }
        }


        @Override
        public int getCount() {
            return mPersons.size();
        }

        @Override
        public Person getItem(int position) {
            return mPersons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
            }

            final Person person = getItem(position);

            LinearLayout ll_task = (LinearLayout) rowView.findViewById(R.id.ll_task);

            TextView titleName = (TextView) rowView.findViewById(R.id.tv_name);
            TextView titleAge = (TextView) rowView.findViewById(R.id.tv_age);

            titleName.setText(person.getName());
            titleAge.setText(person.getAge());


            ll_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskItemListener.onTaskClick(person);
                }
            });


            return rowView;
        }
    }

    public interface TaskItemListener {

        void onTaskClick(Person clickedTask);


    }
}
