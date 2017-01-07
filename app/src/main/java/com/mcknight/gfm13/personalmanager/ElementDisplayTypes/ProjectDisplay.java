package com.mcknight.gfm13.personalmanager.ElementDisplayTypes;

import android.view.View;

import com.mcknight.gfm13.personalmanager.ElementDisplayFragment;
import com.mcknight.gfm13.personalmanager.Sorting.MergeSorter;
import com.mcknight.gfm13.personalmanager.Sorting.SortAlgorithm;
import com.mcknight.gfm13.personalmanager.ViewFactory;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;
import com.mcknight.gfm13.personalmanager.WorkItems.Project;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;
import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 10/7/2016.
 */

public class ProjectDisplay extends ElementDisplayFragment {
    protected List<View> getPageElements(SortAlgorithm algorithm) {
        List<View> elements = new ArrayList<>();

        List<Project> projects = ItemManager.getProjectManager().getItems();
        if (!projects.isEmpty()) {
            List<WorkItem> items =
                    new MergeSorter(algorithm, projects.toArray(new WorkItem[projects.size()])).getSorted();
            int numberOfTasks = ItemManager.getProjectManager().getItems().size();
            for (int i = 0; i < numberOfTasks; i++) {
                elements.add(ViewFactory.makeProjectView((Project) items.get(i), getContext()));
            }
        }
        return elements;
    }
    public ProjectDisplay()
    {
        pageTitle = "All Projects";
    }

}
