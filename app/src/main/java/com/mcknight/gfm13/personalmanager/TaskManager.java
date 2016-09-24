package com.mcknight.gfm13.personalmanager;

/**
 * Created by gfm13 on 9/12/2016.
 */
import android.content.Context;

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
    private List<Task> taskQueue;

    public void Init(Context context){
        this.context = context;
        File tasksLocation = new File(context.getFilesDir(), "Tasks");
        List<JSONObject> objects = (new JSONConverter(tasksLocation)).GetJSONObjects();

        if (taskQueue == null){
            taskQueue = new LinkedList<>();
        }

        for (JSONObject object : objects) {
            taskQueue.add(Task.TaskFromJSON(object));
        }
    }

    public void Commit(){
        List<JSONObject> objects = new ArrayList<>();
        for (Task task: taskQueue) {
            objects.add(task.ToJSON());
        }

        File tasksLocation = new File(context.getFilesDir(), "Tasks");
        (new JSONConverter(tasksLocation)).PutJSONObjects(objects);
    }

    public List<Task> GetTasks() {
        if (taskQueue == null){
            taskQueue = new LinkedList<>();
        }
        return taskQueue;
    }

    public void RemoveTask(Task task)
    {
        GetTasks().remove(task);
    }

    public void AddTask(Task task)
    {
        GetTasks().add(task);
    }
}
