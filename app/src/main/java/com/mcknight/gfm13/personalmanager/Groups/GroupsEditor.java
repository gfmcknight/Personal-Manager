package com.mcknight.gfm13.personalmanager.Groups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mcknight.gfm13.personalmanager.R;
import com.mcknight.gfm13.personalmanager.ViewFactory;

import java.util.ArrayList;

public class GroupsEditor extends AppCompatActivity implements OnGroupRemovalListener {

    LinearLayout oldGroups;
    LinearLayout newGroups;
    ArrayList<String> removedGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_editor);
        oldGroups = (LinearLayout)findViewById(R.id.linear_prev_groups);
        newGroups = (LinearLayout)findViewById(R.id.linear_new_groups);
        ArrayList<String> groups = GroupManager.getInstance().getGroups();
        removedGroups = new ArrayList<String>();

        for(int i = 0; i < groups.size(); i++)
        {
            oldGroups.addView(ViewFactory.makeGroupItemView(groups.get(i), this, this));
        }
    }

    public void onGroupRemoval(String name, View view) {
        if (view.getParent() == oldGroups) {
            removedGroups.add(name);
            oldGroups.removeView(view);
        }
        else {
            newGroups.removeView(view);
        }
    }

    public void addButtonPressed(View view) {
        newGroups.addView(ViewFactory.makeGroupItemView(this, this));
    }

    public void submitPressed (View view) {
        for (String group: removedGroups) {
            GroupManager.getInstance().removeGroup(group);
        }

        for (int i = 0; i < newGroups.getChildCount(); i++)
        {
            LinearLayout newGroupView = (LinearLayout)newGroups.getChildAt(i);
            if (!((EditText)newGroupView.getChildAt(0)).getText().toString().equals("")) {
                GroupManager.getInstance().addGroup(
                        ((EditText)newGroupView.getChildAt(0)).getText().toString());
            }
        }
        finish();
    }


}
