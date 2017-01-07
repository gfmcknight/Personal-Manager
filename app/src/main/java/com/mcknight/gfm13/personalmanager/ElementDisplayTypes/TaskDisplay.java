package com.mcknight.gfm13.personalmanager.ElementDisplayTypes;

import android.view.View;

import com.mcknight.gfm13.personalmanager.ElementDisplayFragment;
import com.mcknight.gfm13.personalmanager.Sorting.GroupSort;
import com.mcknight.gfm13.personalmanager.Sorting.MergeSorter;
import com.mcknight.gfm13.personalmanager.Sorting.SortAlgorithm;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;
import com.mcknight.gfm13.personalmanager.ViewFactory;
import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 10/7/2016.
 */

public class TaskDisplay extends ElementDisplayFragment {
    protected List<View> getPageElements(SortAlgorithm algorithm) {
        List<View> elements = new ArrayList<>();

        List<Task> tasks = ItemManager.getTaskManager().getItems();
        if (! tasks.isEmpty()) {
            List<WorkItem> items =
                    new MergeSorter(algorithm, tasks.toArray(new WorkItem[tasks.size()])).getSorted();
            int numberOfTasks = ItemManager.getTaskManager().getItems().size();
            for (int i = 0; i < numberOfTasks; i++) {
                elements.add(ViewFactory.makeTaskView((Task) items.get(i), getContext()));
            }
        }
        return elements;
    }

    public TaskDisplay() {
        pageTitle = "All Tasks";
    }
}
