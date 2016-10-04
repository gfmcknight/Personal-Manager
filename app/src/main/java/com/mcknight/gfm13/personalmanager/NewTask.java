package com.mcknight.gfm13.personalmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class NewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void SubmitPressed(View view){
        String taskName = ((EditText)findViewById(R.id.taskName)).getText().toString();
        String taskDescription = ((EditText)findViewById(R.id.taskDescription)).getText().toString();
        DatePicker date = (DatePicker)findViewById(R.id.datePicker);
        float timeEstimate = Float.parseFloat(((EditText)findViewById(R.id.timeEstimate)).getText().toString());

        Task newTask = new Task(taskName, "", taskDescription, timeEstimate, 0f,
                date.getYear(), date.getMonth(), date.getDayOfMonth());
        TaskManager.getInstance().AddTask(newTask);
        TaskManager.getInstance().Commit();
        finish();
    }

}
