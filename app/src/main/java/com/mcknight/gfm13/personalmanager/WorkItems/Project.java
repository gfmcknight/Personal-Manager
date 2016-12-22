package com.mcknight.gfm13.personalmanager.WorkItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by gfm13 on 11/18/2016.
 */

public class Project extends WorkItem {

    private int id;
    private float timeWorked;

    private List<String> steps;
    private List<Double> timeEstimates;
    private List<Boolean> completed;

    public Project(String name, String groupName, int yearDue, int monthDue, int dayDue, int id, List<String> steps,
                   List<Double> timeEstimates, List<Boolean> completed){
        super(name, groupName, yearDue, monthDue, dayDue, id);
        this.steps = steps;
        this.timeEstimates = timeEstimates;
        this.completed = completed;
    }

    public Project(JSONObject object) throws JSONException {
        super(object);
        int numberOfSteps = object.getInt("NumberOfSteps");

        steps = new ArrayList<>();
        timeEstimates =  new ArrayList<>();
        completed = new ArrayList<>();

        for (int i = 0 ; i < numberOfSteps; i++) {
            steps.add(object.getString("StepName" + i));
            timeEstimates.add(object.getDouble("StepTime" + i));
            completed.add(object.getBoolean("StepDone" + i));
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        try {
            jsonObject.put("NumberOfSteps", steps.size());
            for (int i = 0; i < steps.size(); i++) {
                jsonObject.put("StepName" + i, steps.get(i));
                jsonObject.put("StepTime" + i, timeEstimates.get(i));
                jsonObject.put("StepDone" + i, completed.get(i));
            }
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
