package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

/**
 * Created by gfm13 on 1/5/2017.
 */

public class GroupSort extends SortAlgorithm{
    public GroupSort(SortAlgorithm fallBack) {
        super(fallBack);
    }

    @Override
    public int evaluate(WorkItem item1, WorkItem item2) {
        return item1.getGroupName().compareTo(item2.getGroupName());
    }

}
