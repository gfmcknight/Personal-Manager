package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

/**
 * Created by gfm13 on 1/5/2017.
 */

public class DueDateSort extends SortAlgorithm {
    public DueDateSort(SortAlgorithm fallBack) {
        super(fallBack);
    }

    @Override
    public int evaluate(WorkItem item1, WorkItem item2) {
        return item1.getDateDue().compareTo(item2.getDateDue());
    }
}
