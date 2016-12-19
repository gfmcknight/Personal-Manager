package com.mcknight.gfm13.personalmanager.WorkItems;

import java.util.Calendar;

import org.json.*;

/**
 * Created by gfm13 on 9/12/2016.
 */
public class Task extends WorkItem {

    private String description;
    private float hoursEstimate;

    public Task(String name, String groupName, String description, float hoursEstimate, float timeWorked,
                int yearDue, int monthDue, int dayDue, int id) {

        super(name, groupName, yearDue, monthDue, dayDue, id);


        setDescription(description);
        setHoursEstimate(hoursEstimate);

        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();

    }

    public JSONObject toJSON(){
        JSONObject jsonObject = super.toJSON();
        try {
            jsonObject.put("Name", getName());
            jsonObject.put("Group", getGroupName());
            jsonObject.put("Description", getDescription());
            jsonObject.put("HoursEstimate", getHoursEstimate());
            return jsonObject;

        } catch (Exception e) {
            return null;
        }
    }

    public Task (JSONObject object) throws JSONException {
        this(object.getString("Name"), object.getString("Group"), object.getString("Description"),
            (float)object.getDouble("HoursEstimate"), (float)object.getDouble("TimeWorked"),
            object.getInt("Year"), object.getInt("Month"), object.getInt("Day"), object.getInt("Id"));
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHoursEstimate() {
        return hoursEstimate;
    }

    public void setHoursEstimate(float hoursEstimate) {
        this.hoursEstimate = hoursEstimate;
    }
}
