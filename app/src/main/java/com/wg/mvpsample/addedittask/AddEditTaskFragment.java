package com.wg.mvpsample.addedittask;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wg.mvpsample.R;
import com.wg.mvpsample.data.Person;

import de.greenrobot.event.EventBus;

/**
 */
public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {
    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    private Button add_submit;

    private EditText add_task_name, add_task_age;

    private View root;

    private AddEditTaskContract.Presenter presenter;

    private AddEditTasksPresenter addEditTasksPresenter;

    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    public AddEditTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        initView();
        return root;
    }

    private void initView() {
        add_task_name = (EditText) root.findViewById(R.id.add_task_title);
        add_task_age = (EditText) root.findViewById(R.id.add_task_description);
        add_submit = (Button) root.findViewById(R.id.add_task_submit);
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_task_name.getText().toString();
                String age = add_task_age.getText().toString();
                presenter.addViewData(new Person(name, age));
            }
        });

        addEditTasksPresenter = new AddEditTasksPresenter(this);
    }

    @Override
    public void postViewData(Person p) {
        EventBus.getDefault().post(p);
        getActivity().finish();
    }
}
