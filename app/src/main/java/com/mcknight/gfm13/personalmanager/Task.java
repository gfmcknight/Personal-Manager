package com.mcknight.gfm13.personalmanager;

import java.util.Calendar;
import java.util.Date;
import org.json.*;

/**
 * Created by gfm13 on 9/12/2016.
 */
public class Task {

    public String Name;
    public String GroupName;
    public String Description;
    public float HoursEstimate;
    public float TimeWorked;

    private int yearDue;
    private int monthDue;
    private int dayDue;
    Date dateDue;

    public Task(String name, String groupName, String description, float hoursEstimate, float timeWorked,
                int yearDue, int monthDue, int dayDue) {

        Name = name;
        GroupName = groupName;
        Description = description;
        HoursEstimate = hoursEstimate;
        TimeWorked = timeWorked;

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
            jsonObject.put("Name", Name);
            jsonObject.put("Group", GroupName);
            jsonObject.put("Description", Description);
            jsonObject.put("HoursEstimate", HoursEstimate);
            jsonObject.put("TimeWorked", TimeWorked);
            jsonObject.put("Year", yearDue);
            jsonObject.put("Month", monthDue);
            jsonObject.put("Day", dayDue);
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
                    object.getInt("Year"), object.getInt("Month"), object.getInt("Day"));
        }
        catch (Exception e){
            return null;
        }

    }
}
