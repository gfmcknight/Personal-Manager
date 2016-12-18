package com.mcknight.gfm13.personalmanager;

import java.util.Calendar;
import java.util.Date;
import org.json.*;

/**
 * Created by gfm13 on 9/12/2016.
 */
public class Task {

    private String Name;
    private String groupName;
    private String description;
    private float hoursEstimate;
    private float timeWorked;

    private int yearDue;
    private int monthDue;
    private int dayDue;
    Date dateDue;

    private int id;

    public Task(String name, String groupName, String description, float hoursEstimate, float timeWorked,
                int yearDue, int monthDue, int dayDue, int id) {

        setName(name);
        setGroupName(groupName);
        setDescription(description);
        setHoursEstimate(hoursEstimate);
        setTimeWorked(timeWorked);

        this.yearDue = yearDue;
        this.monthDue = monthDue;
        this.dayDue = dayDue;

        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();

    }

    public JSONObject ToJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", getName());
            jsonObject.put("Group", getGroupName());
            jsonObject.put("Description", getDescription());
            jsonObject.put("HoursEstimate", getHoursEstimate());
            jsonObject.put("TimeWorked", getTimeWorked());
            jsonObject.put("Year", yearDue);
            jsonObject.put("Month", monthDue);
            jsonObject.put("Day", dayDue);
            jsonObject.put("Id", id);
            return jsonObject;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Task TaskFromJSON(JSONObject object){
        try {
            return new Task(object.getString("Name"), object.getString("Group"), object.getString("Description"),
                    (float)object.getDouble("HoursEstimate"), (float)object.getDouble("TimeWorked"),
                    object.getInt("Year"), object.getInt("Month"), object.getInt("Day"), object.getInt("Id"));
        }
        catch (Exception e) {
            return null;
        }
    }

    public  int getId(){
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public float getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(float timeWorked) {
        this.timeWorked = timeWorked;
    }

    public int getYearDue() {
        return yearDue;
    }

    public void setYearDue(int yearDue) {
        this.yearDue = yearDue;
        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();
    }

    public int getMonthDue() {
        return monthDue;
    }

    public void setMonthDue(int monthDue) {
        this.monthDue = monthDue;
        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();
    }

    public int getDayDue() {
        return dayDue;
    }

    public void setDayDue(int dayDue) {
        this.dayDue = dayDue;
        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();
    }
}
