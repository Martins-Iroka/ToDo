package com.martdev.android.todo.view.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.martdev.android.todo.R;
import com.martdev.android.todo.repository.model.ToDo;
import com.martdev.android.todo.utils.InjectorUtils;
import com.martdev.android.todo.utils.ToDoDateUtils;
import com.martdev.android.todo.viewmodel.ToDoAddEditModel.ToDoAddEditModelFactory;
import com.martdev.android.todo.viewmodel.ToDoAddEditModel.ToDoAddEditViewModel;

import java.util.Date;
import java.util.Objects;

public class AddEditTodoFragment extends Fragment {
    private static final String ARG_ID = "date";
    private static final int REQUEST_DATE = 1;

    private ToDoAddEditViewModel mViewModel;

    private TextInputEditText mToDoField, mDateField;
    private ImageButton mDateButton;
    private CheckBox mTodoCheck;
    private int id;
    private long dateFromPicker = -1;
    private boolean isTodoDone;

    public static AddEditTodoFragment newInstance(int id) {
        Bundle arg = new Bundle();
        arg.putInt(ARG_ID, id);

        AddEditTodoFragment fragment = new AddEditTodoFragment();
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_todo, container, false);

        id = getArguments().getInt(ARG_ID);

        ToDoAddEditModelFactory factory = InjectorUtils.provideToDoAddEditModelFactory(Objects.requireNonNull(getActivity()));

        mViewModel = ViewModelProviders.of(this, factory).get(ToDoAddEditViewModel.class);
        validateID(id);

        mToDoField = view.findViewById(R.id.todo_field);

        mDateField = view.findViewById(R.id.date_todo);
        mDateField.setEnabled(false);

        mTodoCheck = view.findViewById(R.id.todo_check);
        mTodoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTodoDone = isChecked;
                if (isChecked) {
                    buttonView.setText(R.string.todo_done);
                    buttonView.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    int color = getActivity().getResources().getColor(android.R.color.holo_red_light);
                    buttonView.setText(R.string.todo_not_done);
                    buttonView.setTextColor(color);
                }
            }
        });

        mDateButton = view.findViewById(R.id.date_button);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == -1) {
                    if (dateFromPicker < 0) {
                        Date date = new Date();
                        showDialog(date);
                    } else {
                        showDialog(new Date(dateFromPicker));
                    }
                } else {
                    mViewModel.getToDo(id).observe(getActivity(), new Observer<ToDo>() {
                        @Override
                        public void onChanged(@Nullable ToDo toDo) {
                            showDialog(toDo.getDate());
                        }
                    });
                }
            }
        });

        return view;
    }

    private void showDialog(Date date) {
        FragmentManager manager = getFragmentManager();
        DatePickerDialog dialog = DatePickerDialog.newInstance(date);
        dialog.setTargetFragment(AddEditTodoFragment.this, REQUEST_DATE);
        dialog.show(manager, "Dialog");
    }

    private void validateID(int id) {
        if (id == -1) {
            getActivity().setTitle("Add Todo");
        } else {
            getActivity().setTitle("Edit ToDo");
            mViewModel.getToDo(id).observe(getActivity(), new Observer<ToDo>() {
                @Override
                public void onChanged(@Nullable ToDo toDo) {
                    loadToDo(toDo);
                }
            });
        }
    }

    private void loadToDo(ToDo toDo) {
        Objects.requireNonNull(toDo, "its null o!");
        String whatToDo = toDo.getWhatToDo();
        dateFromPicker = toDo.getDate().getTime();
        String dateToDo = ToDoDateUtils.getReadableDate(dateFromPicker);

        mToDoField.setText(whatToDo);
        mDateField.setText(dateToDo);
        mTodoCheck.setChecked(toDo.isCompleted());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_task_option, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_save) {
            String whatTodo = mToDoField.getText().toString();
            String dateTo = mDateField.getText().toString();
            if (whatTodo.isEmpty() && dateTo.isEmpty()) {
                Snackbar snackbar = Snackbar.make(getView(), "This field can't be blank", Snackbar.LENGTH_LONG);
                snackbar.show();
                return false;
            } else if (id == -1) {
                ToDo toDo = new ToDo(whatTodo, new Date(dateFromPicker), isTodoDone);
                mViewModel.insertToDo(toDo);
            } else {
                ToDo toDo = new ToDo(whatTodo, new Date(dateFromPicker), isTodoDone);
                toDo.setId(id);
                mViewModel.updateToDo(toDo);
            }
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            dateFromPicker = DatePickerDialog.setDate(data).getTime();
            String dateString = ToDoDateUtils.getReadableDate(dateFromPicker);
            mDateField.setText(dateString);
        }
    }
}
