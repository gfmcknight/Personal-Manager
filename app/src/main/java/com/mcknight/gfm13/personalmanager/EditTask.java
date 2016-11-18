package com.mcknight.gfm13.personalmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEventType;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;

public class EditTask extends AppCompatActivity {

    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int taskID = getIntent().getIntExtra(getString(R.string.edit_ID), 0);
        task = TaskManager.getInstance().getTaskByID(taskID);

        ((EditText)findViewById(R.id.taskName)).setText(task.Name);
        ((EditText)findViewById(R.id.taskDescription)).setText(task.Description);
    }

    public void SubmitPressed(View view) {
        task.Name = ((EditText)findViewById(R.id.taskName)).getText().toString();
        task.Description = ((EditText)findViewById(R.id.taskDescription)).getText().toString();
        TaskManager.getInstance().Commit();

        RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.EDIT, task));
        finish();
    }
}
