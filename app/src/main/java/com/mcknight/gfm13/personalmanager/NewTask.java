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


public class NewTask extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListPopupWindow groupsPopupWindow;
    DateChoiceHandler datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        groupsPopupWindow = GroupManager.getInstance().getGroupsAdapter(this, this);

        if (groupsPopupWindow != null) {
            groupsPopupWindow.setAnchorView((EditText) findViewById(R.id.groupSelection));

            ((EditText) findViewById(R.id.groupSelection)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    groupsPopupWindow.show();
                }
            });
        }

        datePicker = ViewFactory.makeDateView(this);
        ((LinearLayout)findViewById(R.id.datePicker)).addView(datePicker.getLayout());
    }

    public void submitPressed(View view){

        String taskName = ((EditText)findViewById(R.id.taskName)).getText().toString();
        String taskDescription = ((EditText)findViewById(R.id.taskDescription)).getText().toString();
        String groupName = ((EditText)findViewById(R.id.groupSelection)).getText().toString();
        float timeEstimate = 1;
        try {
            timeEstimate = Float.parseFloat(((EditText) findViewById(R.id.timeEstimate)).getText().toString());
        } catch (NumberFormatException e)
        {
            String[] timeSections = ((EditText) findViewById(R.id.timeEstimate)).getText().toString().split(":");
            if (timeSections.length == 2){
                try {
                    timeEstimate = Float.parseFloat(timeSections[0]) + Float.parseFloat(timeSections[1]) / 60;

                } catch (NumberFormatException f) {}
            }
        }
        Task newTask = new Task(taskName, groupName, taskDescription, timeEstimate, 0f,
                datePicker.getYear(), datePicker.getMonth(), datePicker.getDay(),
                TaskManager.getInstance().getNewID());
        TaskManager.getInstance().addTask(newTask);
        TaskManager.getInstance().commit();

        RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.ADD, newTask));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        ((EditText)findViewById(R.id.groupSelection)).setText(GroupManager.getInstance().getGroup(position));
        groupsPopupWindow.dismiss();
    }
}
