package com.mcknight.gfm13.personalmanager.WorkItems;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gfm13 on 12/19/2016.
 */

public interface ItemFactory {
    public abstract WorkItem makeItem(JSONObject object);
}
