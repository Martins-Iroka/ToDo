package com.martdev.android.todo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.martdev.android.todo.R;
import com.martdev.android.todo.view.fragment.AddEditTodoFragment;

public class AddEditTodoActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "com.martdev.android.todo.view.extra_id";

    public static Intent newIntent(Context packageContext, int id) {
        Intent intent = new Intent(packageContext, AddEditTodoActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = AddEditTodoFragment.newInstance(id);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
