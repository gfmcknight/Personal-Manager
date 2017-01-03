package com.mcknight.gfm13.personalmanager.ElementDisplayTypes;

import android.view.View;

import com.mcknight.gfm13.personalmanager.ElementDisplayFragment;
import com.mcknight.gfm13.personalmanager.ViewFactory;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;
import com.mcknight.gfm13.personalmanager.WorkItems.Project;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 10/7/2016.
 */

public class ProjectDisplay extends ElementDisplayFragment {
    protected List<View> getPageElements() {
        List<View> elements = new ArrayList<>();

        List<Project> projects = ItemManager.getProjectManager().getItems();
        int numberOfTasks = ItemManager.getProjectManager().getItems().size();
        for (int i = 0; i < numberOfTasks; i++)
        {
            elements.add(ViewFactory.makeProjectView(projects.get(i), getContext()));
        }

        return elements;
    }
    public ProjectDisplay()
    {
        pageTitle = "All Projects";
    }

}
