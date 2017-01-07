package com.mcknight.gfm13.personalmanager.WorkItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by gfm13 on 11/18/2016.
 */

public class Project extends WorkItem {

    private List<String> steps;
    private List<Double> timeEstimates;
    private List<Boolean> completed;

    private long startDate;

    public Project(String name, String groupName, int yearDue, int monthDue, int dayDue, int id, List<String> steps,
                   List<Double> timeEstimates, List<Boolean> completed){
        super(name, groupName, yearDue, monthDue, dayDue, id);
        this.steps = steps;
        this.timeEstimates = timeEstimates;
        this.completed = completed;
        this.startDate = new Date().getTime();
    }

    public Project(JSONObject object) throws JSONException {
        super(object);
        int numberOfSteps = object.getInt("NumberOfSteps");

        if (object.has("Started")) {
            this.startDate = object.getLong("Started");
        } else {
            this.startDate = new Date().getTime();
        }

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
            jsonObject.put("Started", startDate);
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

    public double getCompletion(){
        double completedHours = 0d;
        double totalHours = 0d;
        for (int i = 0; i < steps.size(); i++) {
            totalHours += timeEstimates.get(i);
            if (completed.get(i)){
                completedHours += timeEstimates.get(i);
            }
        }
        if (completedHours == 0) {
            return 0;
        } else {
            return completedHours / totalHours;
        }
    }

    public boolean isPriority() {
        double timePassedRatio = (double)(new Date().getTime() - getStartDate()) /
                (getDateDue().getTime() - getStartDate());
        return (timePassedRatio - getCompletion() > 0.05) || getCompletion() > 0.9;
    }

    public void updateSteps(List<String> steps, List<Double> timeEstimates) {
        List<String> completedSteps = new LinkedList<>();
        for(int i = 0; i < completed.size(); i++) {
            if (completed.get(i)) {
                completedSteps.add(this.steps.get(i));
            }
        }

        this.steps = steps;
        this.timeEstimates = timeEstimates;
        completed.clear();

        for (int i = 0; i < steps.size(); i++) {
            if (completedSteps.contains(steps.get(i))) {
                completed.add(true);
            } else {
                completed.add(false);
            }
        }
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public List<String> getSteps() {
        return steps;
    }

    public List<Double> getTimeEstimates() {
        return timeEstimates;
    }

    public List<Boolean> getCompleted() {
        return completed;
    }

    public void toggleCompletion(int index) {
        completed.set(index, !completed.get(index));
    }
}
