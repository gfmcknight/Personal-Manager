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
import com.mcknight.gfm13.personalmanager.WorkItems.Project;

import java.util.ArrayList;

public class NewProject extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListPopupWindow groupsPopupWindow;
    DateChoiceHandler datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
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

    public void addButtonPressed(View view) {
        LinearLayout stepList = ((LinearLayout)findViewById(R.id.stepLayout));
        stepList.addView(ViewFactory.makeStepView(this, stepList));
    }

    public void submitPressed (View view) {
        String name = ((EditText)findViewById(R.id.projectName)).getText().toString();
        String groupName = ((EditText)findViewById(R.id.groupSelection)).getText().toString();

        ArrayList<String> steps = new ArrayList<>();
        ArrayList<Double> timeEstimates = new ArrayList<>();
        ArrayList<Boolean> completed = new ArrayList<>();

        LinearLayout stepList = ((LinearLayout)findViewById(R.id.stepLayout));

        for (int i = 0; i < stepList.getChildCount(); i++){
            steps.add(((EditText)((LinearLayout)stepList.getChildAt(i)).getChildAt(1)).getText().toString());
            try {
                timeEstimates.add(Double.parseDouble(((EditText)((LinearLayout)stepList.getChildAt(i)).getChildAt(2)).getText().toString()));
            } catch (NumberFormatException e) {
                timeEstimates.add(1d);
            }

            completed.add(false);
        }

        Project newProject = new Project(name, groupName, datePicker.getYear(), datePicker.getMonth(), datePicker.getDay(),
                ItemManager.getProjectManager().getNewID(), steps, timeEstimates, completed);
        ItemManager.getProjectManager().addItem(newProject);
        ItemManager.getProjectManager().commit();
        RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.ADD, newProject));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        ((EditText)findViewById(R.id.groupSelection)).setText(GroupManager.getInstance().getGroup(position));
        groupsPopupWindow.dismiss();
    }

}
