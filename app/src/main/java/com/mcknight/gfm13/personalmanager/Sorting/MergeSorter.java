package com.mcknight.gfm13.personalmanager.Sorting;

import com.mcknight.gfm13.personalmanager.WorkItems.WorkItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gfm13 on 1/5/2017.
 */

public class MergeSorter {
    public static final SortAlgorithm DEFAULT_SORT =
            new DueDateSort(new GroupSort(new NameSort(new IdSort(new NullSort()))));

    private List<WorkItem> sorted;

    public MergeSorter(SortAlgorithm sorter, WorkItem[] items) {
        WorkItem[][] arrays = new WorkItem[items.length][];
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = new WorkItem[] { items[i] };
        }

        while (arrays.length > 1) {
            WorkItem[][] mergedArrays = new WorkItem[(arrays.length + 1) / 2][];
            for (int i = 0; i < arrays.length - 1; i += 2) {
                mergedArrays[i / 2] = mergeArrays(arrays[i], arrays[i + 1], sorter);
            }
            if (arrays.length % 2 == 1) {
                mergedArrays[mergedArrays.length - 1] = arrays[arrays.length - 1];
            }
            arrays = mergedArrays;
        }

        sorted = Arrays.asList(arrays[0]);
    }

    private WorkItem[] mergeArrays (WorkItem[] array1, WorkItem[] array2, SortAlgorithm sorter)
    {
        WorkItem[] result = new WorkItem[array1.length + array2.length];
        for (int i = 0, j = 0; i < array1.length || j < array2.length; ) {
            if (j >= array2.length) {
                result[i + j] = array1[i];
                i++;
                continue;
            } else if (i >= array1.length) {
                result[i + j] = array2[j];
                j++;
                continue;
            } else
            {
                if (sorter.compare(array1[i], array2[j]) < 0) {
                    result[i + j] = array1[i];
                    i++;
                } else {
                    result[i + j] = array2[j];
                    j++;
                }
            }
        }
        return result;
    }

    public List<WorkItem> getSorted() {
        return sorted;
    }

}
