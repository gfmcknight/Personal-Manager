package com.mcknight.gfm13.personalmanager;

/**
 * Created by gfm13 on 9/12/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;

import org.json.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskManager {
    private static TaskManager ourInstance = new TaskManager();

    public static TaskManager getInstance() {
        return ourInstance;
    }

    private Context context;
    private List<Task> taskList;
    int currentID;
    SharedPreferences preferences;

    private boolean initialized = false;

    public void init(Context context){
        if (!initialized) {
            this.context = context;
            File tasksLocation = new File(context.getFilesDir(), "Tasks");
            List<JSONObject> objects = (new JSONConverter(tasksLocation)).GetJSONObjects();

            if (taskList == null) {
                taskList = new LinkedList<>();
            }

            for (JSONObject object : objects) {
                taskList.add(Task.TaskFromJSON(object));
            }
            initialized = true;

            preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        }
    }

    public void commit(){
        List<JSONObject> objects = new ArrayList<>();
        for (Task task: taskList) {
            objects.add(task.ToJSON());
        }

        File tasksLocation = new File(context.getFilesDir(), "Tasks");
        (new JSONConverter(tasksLocation)).PutJSONObjects(objects);
    }

    public List<Task> getTasks() {
        if (taskList == null){
            taskList = new LinkedList<>();
        }
        return taskList;
    }

    public void RemoveTask(Task task)
    {
        getTasks().remove(task);
    }

    public void addTask(Task task)
    {
        getTasks().add(task);
    }

    public int getNewID() {
        int currentIDValue = preferences.getInt("ID_count", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID_count", currentIDValue + 1);
        editor.commit();
        return currentIDValue;
    }

    public Task getTaskByID(int id) {
        for (Task task : taskList) {
            if (id == task.getId()) {
                return task;
            }
        }
        return null;
    }
}
