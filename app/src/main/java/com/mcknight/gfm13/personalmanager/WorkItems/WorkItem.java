package com.mcknight.gfm13.personalmanager.WorkItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gfm13 on 12/19/2016.
 */

public abstract class WorkItem {

    private int id;
    private String name;
    private String groupName;

    private int yearDue;
    private int monthDue;
    private int dayDue;
    private Date dateDue;

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", getName());
            jsonObject.put("Group", getGroupName());
            jsonObject.put("Year", getYearDue());
            jsonObject.put("Month", getMonthDue());
            jsonObject.put("Day", getDayDue());
            jsonObject.put("Id", getId());
            return jsonObject;
        }
        catch (JSONException e)
        {
            return null;
        }
    }

    public WorkItem(String name, String groupName, int yearDue, int monthDue, int dayDue, int id) {
        setName(name);
        setGroupName(groupName);

        setYearDue(yearDue);
        setMonthDue(monthDue);
        setDayDue(dayDue);

        setId(id);

        Calendar dateSetter = Calendar.getInstance();
        dateSetter.set(Calendar.YEAR, yearDue);
        dateSetter.set(Calendar.MONTH, monthDue);
        dateSetter.set(Calendar.DAY_OF_MONTH, dayDue);
        dateDue = dateSetter.getTime();
    }

    public WorkItem(JSONObject object) throws JSONException
    {
        this(object.getString("Name"), object.getString("Group"), object.getInt("Year"), object.getInt("Month"),
                object.getInt("Day"), object.getInt("Id"));
    }

    public abstract boolean isPriority();

    private void setId(int id) {
        this.id = id;
    }

    public  int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public int getYearDue(){
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
