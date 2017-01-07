package com.mcknight.gfm13.personalmanager.WorkItems;

import java.util.Calendar;
import java.util.Date;

import org.json.*;

/**
 * Created by gfm13 on 9/12/2016.
 */
public class Task extends WorkItem {

    private static final long MS_PER_DAY = 86400000;
    private static final long LAZY_MS_PER_DAY = 87000000;

    private String description;
    private float hoursEstimate;

    public Task(String name, String groupName, String description, float hoursEstimate,
                int yearDue, int monthDue, int dayDue, int id) {

        super(name, groupName, yearDue, monthDue, dayDue, id);


        setDescription(description);
        setHoursEstimate(hoursEstimate);

    }

    public JSONObject toJSON(){
        JSONObject jsonObject = super.toJSON();
        try {
            jsonObject.put("Description", getDescription());
            jsonObject.put("HoursEstimate", getHoursEstimate());
            return jsonObject;

        } catch (Exception e) {
            return null;
        }
    }

    public Task (JSONObject object) throws JSONException {
        this(object.getString("Name"), object.getString("Group"), object.getString("Description"),
            (float)object.getDouble("HoursEstimate"), object.getInt("Year"), object.getInt("Month"),
            object.getInt("Day"), object.getInt("Id"));
    }


    @Override
    public boolean isPriority() {
        long timeLeft = getDateDue().getTime() - (new Date()).getTime();
        return timeLeft <= LAZY_MS_PER_DAY || getHoursEstimate() / (timeLeft / MS_PER_DAY) >= 1.9;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public float getHoursEstimate() {
        return hoursEstimate;
    }

    public void setHoursEstimate(float hoursEstimate) {
        this.hoursEstimate = hoursEstimate;
    }
}
