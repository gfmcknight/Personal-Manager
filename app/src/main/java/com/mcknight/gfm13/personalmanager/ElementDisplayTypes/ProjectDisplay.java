package com.mcknight.gfm13.personalmanager.ElementDisplayTypes;

import android.view.View;

import com.mcknight.gfm13.personalmanager.ElementDisplayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 10/7/2016.
 */

public class ProjectDisplay extends ElementDisplayFragment {
    protected List<View> getPageElements() {
        List<View> elements = new ArrayList<>();
        return elements;
    }
    public ProjectDisplay()
    {
        super();
        pageTitle = "All Projects";
    }

}
