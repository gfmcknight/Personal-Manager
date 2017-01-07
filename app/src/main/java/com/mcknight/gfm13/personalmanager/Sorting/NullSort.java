package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

/**
 * Created by gfm13 on 1/5/2017.
 */

public class NullSort extends SortAlgorithm {

    public NullSort() {
        super(null);
    }

    @Override
    public int compare(WorkItem item1, WorkItem item2) {
        return 0;
    }

    @Override
    public int evaluate(WorkItem item1, WorkItem item2) {
        return 0;
    }
}
