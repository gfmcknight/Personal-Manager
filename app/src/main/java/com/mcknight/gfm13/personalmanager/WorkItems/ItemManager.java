package com.mcknight.gfm13.personalmanager.WorkItems;

/**
 * Created by gfm13 on 9/12/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.mcknight.gfm13.personalmanager.JSONConverter;
import com.mcknight.gfm13.personalmanager.R;

import org.json.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ItemManager<T extends WorkItem>  {
    private static final long LAZY_MS_PER_DAY = 85000000;

    private static ItemManager<Task> taskManager = new ItemManager<>(new TaskFactory(), "Tasks");
    private static ItemManager<Project> projectManager = new ItemManager<>(new ProjectFactory(), "Projects");

    public static ItemManager<Task> getTaskManager() {
        return taskManager;
    }
    public static ItemManager<Project> getProjectManager() {
        return projectManager;
    }

    private Context context;
    private List<T> itemList;
    private int currentID;
    private SharedPreferences preferences;
    private ItemFactory factory;
    private String fileName;

    private ItemManager(ItemFactory factory, String fileName) {
        this.factory = factory;
        this.fileName = fileName;
    }

    private boolean initialized = false;

    public void init(Context context){
        if (!initialized) {
            this.context = context;
            File tasksLocation = new File(context.getFilesDir(), fileName);
            List<JSONObject> objects = (new JSONConverter(tasksLocation)).GetJSONObjects();

            if (itemList == null) {
                itemList = new LinkedList<>();
            }

            for (JSONObject object : objects) {
                try {
                    itemList.add((T) factory.makeItem(object));
                } catch (ClassCastException e) {
                    throw new ClassCastException("The ItemFactory associated with this ItemManager object"+
                            " must create objects of the correct type.");
                }
            }
            initialized = true;

            preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        }
    }

    public void commit(){
        List<JSONObject> objects = new ArrayList<>();
        for (T item: itemList) {
            objects.add(item.toJSON());
        }

        File tasksLocation = new File(context.getFilesDir(), fileName);
        (new JSONConverter(tasksLocation)).PutJSONObjects(objects);
    }

    public void purge() {
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() - LAZY_MS_PER_DAY);
        for (int i = itemList.size() - 1; i >= 0; i--) {
            if ((itemList.get(i)).getDateDue().before(currentDate)) {
                removeItem(itemList.get(i));
            }
        }
    }
    public List<T> getItems() {
        if (itemList == null){
            itemList = new LinkedList<>();
        }
        return itemList;
    }

    public void removeItem(T item)
    {
        getItems().remove(item);
    }

    public void addItem(T item)
    {
        getItems().add(item);
    }

    public int getNewID() {
        int currentIDValue = preferences.getInt("ID_count", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID_count", currentIDValue + 1);
        editor.commit();
        return currentIDValue;
    }

    public T getItemByID(int id) {
        for (T task : itemList) {
            if (id == task.getId()) {
                return task;
            }
        }
        return null;
    }
}
