package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

/**
 * Created by gfm13 on 1/5/2017.
 */

public abstract class SortAlgorithm {

    private SortAlgorithm fallBack;

    public SortAlgorithm(SortAlgorithm fallBack) {
        this.fallBack = fallBack;
    }

    public int compare(WorkItem item1, WorkItem item2)
    {
        int result = evaluate(item1, item2);
        if (result == 0) {
            return fallBack.compare(item1, item2);
        } else {
            return result;
        }
    }

    public abstract int evaluate(WorkItem item1, WorkItem item2);


}
