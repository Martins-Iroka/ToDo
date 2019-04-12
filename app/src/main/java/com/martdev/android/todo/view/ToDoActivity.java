package com.martdev.android.todo.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.martdev.android.todo.R;
import com.martdev.android.todo.adapter.ToDoAdapter;
import com.martdev.android.todo.repository.model.ToDo;
import com.martdev.android.todo.utils.InjectorUtils;
import com.martdev.android.todo.viewmodel.ToDoListModel.ToDoListModelFactory;
import com.martdev.android.todo.viewmodel.ToDoListModel.ToDoListViewModel;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements
        ToDoAdapter.ToDoOnItemClickHandler {

    private ToDoListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mActionButton;
    private ToDoAdapter mAdapter;
    private TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new ToDoAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ToDoListModelFactory factory = InjectorUtils.provideTodoListModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(ToDoListViewModel.class);
        mViewModel.getTodoList().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(@Nullable List<ToDo> toDos) {
                mAdapter.setToDoList(toDos);
                mMessage.setVisibility(mAdapter.getToDoList().size() > 0 ? View.GONE : View.VISIBLE);
            }
        });

        mActionButton = findViewById(R.id.fab);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddEditTodoActivity.newIntent(ToDoActivity.this, -1);
                startActivity(intent);
            }
        });

        mMessage = findViewById(R.id.empty_list_message);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ToDo toDo = mAdapter.getToDoList().get(viewHolder.getAdapterPosition());
                mViewModel.deleteToDo(toDo);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.completed_todo) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickItem(ToDo toDo) {
        Intent intent = AddEditTodoActivity.newIntent(ToDoActivity.this, toDo.getId());
        startActivity(intent);
    }
}
