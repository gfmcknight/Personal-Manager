package com.mcknight.gfm13.personalmanager.WorkItems;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gfm13 on 12/19/2016.
 */

public class ProjectFactory implements ItemFactory {
    public WorkItem makeItem(JSONObject object) {
        try {
            return new Project(object);
        } catch (JSONException e) {
            return null;
        }
    }
}
