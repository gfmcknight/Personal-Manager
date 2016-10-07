package com.mcknight.gfm13.personalmanager.ElementDisplayTypes;

import android.view.View;

import com.mcknight.gfm13.personalmanager.ElementDisplayFragment;
import com.mcknight.gfm13.personalmanager.Task;
import com.mcknight.gfm13.personalmanager.TaskManager;
import com.mcknight.gfm13.personalmanager.ViewFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 10/7/2016.
 */

public class TaskDisplay extends ElementDisplayFragment {
    protected List<View> getPageElements() {
        List<View> elements = new ArrayList<>();

        List<Task> tasks = TaskManager.getInstance().GetTasks();
        int numberOfTasks = TaskManager.getInstance().GetTasks().size();
        for (int i = 0; i < numberOfTasks; i++)
        {
            elements.add(ViewFactory.makeView(tasks.get(i), getContext()));
        }

        return elements;
    }

    public TaskDisplay() {
        super();
        pageTitle = "All Tasks";
    }
}
