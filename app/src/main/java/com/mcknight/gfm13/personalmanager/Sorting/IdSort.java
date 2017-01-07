package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

/**
 * Created by gfm13 on 1/5/2017.
 */

public class IdSort extends SortAlgorithm {

    public IdSort(SortAlgorithm fallBack) {
        super(fallBack);
    }

    @Override
    public int evaluate(WorkItem item1, WorkItem item2) {
        return Integer.valueOf(item1.getId()).compareTo(item2.getId());
    }
}
