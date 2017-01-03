package com.mcknight.gfm13.personalmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mcknight.gfm13.personalmanager.Groups.GroupManager;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEventType;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;

public class EditTask extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Task task;

    ListPopupWindow groupsPopupWindow;
    DateChoiceHandler datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int taskId = getIntent().getIntExtra(getString(R.string.edit_ID), 0);
        task = ItemManager.getTaskManager().getItemByID(taskId);

        ((EditText)findViewById(R.id.taskName)).setText(task.getName());
        ((EditText)findViewById(R.id.taskDescription)).setText(task.getDescription());
        ((EditText)findViewById(R.id.groupSelection)).setText(task.getGroupName());

        ((EditText)findViewById(R.id.timeEstimate)).setText(Float.valueOf(task.getHoursEstimate()).toString());

        groupsPopupWindow = GroupManager.getInstance().getGroupsAdapter(this, this);

        if (groupsPopupWindow != null) {
            groupsPopupWindow.setAnchorView(findViewById(R.id.groupSelection));

            findViewById(R.id.groupSelection).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    groupsPopupWindow.show();
                }
            });
        }

        datePicker = ViewFactory.makeDateView(this, task.getYearDue(), task.getMonthDue() + 1, task.getDayDue());
        ((LinearLayout)findViewById(R.id.datePicker)).addView(datePicker.getLayout());
    }

    public void submitPressed(View view){

        task.setName(((EditText)findViewById(R.id.taskName)).getText().toString());
        task.setDescription(((EditText)findViewById(R.id.taskDescription)).getText().toString());
        task.setDayDue(datePicker.getDay());
        task.setMonthDue(datePicker.getMonth());
        task.setYearDue(datePicker.getYear());
        task.setGroupName(((EditText)findViewById(R.id.groupSelection)).getText().toString());

        try {
            task.setHoursEstimate(Float.parseFloat(((EditText) findViewById(R.id.timeEstimate)).getText().toString()));
        } catch (NumberFormatException e) {
            String[] timeSections = ((EditText) findViewById(R.id.timeEstimate)).getText().toString().split(":");
            if (timeSections.length == 2){
                try {
                    task.setHoursEstimate(Float.parseFloat(timeSections[0]) + Float.parseFloat(timeSections[1]) / 60);

                } catch (NumberFormatException f) {}
            }
        }


        ItemManager.getTaskManager().commit();

        RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.EDIT, task));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        ((EditText)findViewById(R.id.groupSelection)).setText(GroupManager.getInstance().getGroup(position));
        groupsPopupWindow.dismiss();
    }
}

