package com.mcknight.gfm13.personalmanager.Groups;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mcknight.gfm13.personalmanager.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 12/10/2016.
 */
public class GroupManager {
    private static GroupManager ourInstance = new GroupManager();

    public static GroupManager getInstance() {
        return ourInstance;
    }

    private Context context;
    private List<String> groups;
    private File groupsLocation;

    private boolean initialized = false;

    public  void init(Context context) {
        if (!initialized) {
            this.context = context;

            initialized = true;

            groupsLocation = new File(context.getFilesDir(), "Groups");
            groups = new ArrayList<>();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(groupsLocation));
                String lastLine;
                while ((lastLine = reader.readLine()) != null){
                    if (lastLine == ""){
                        break;
                    }
                    groups.add(lastLine);
                }
                reader.close();
            }
            catch (Exception e){

            }
        }
    }

    public ListPopupWindow getGroupsAdapter(Context context, AdapterView.OnItemClickListener listener) {
        if(groups.size() > 0) {
            ListPopupWindow window = new ListPopupWindow(context);
            window.setAdapter(new ArrayAdapter(context, R.layout.group_item, groups));

            window.setWidth(300);
            window.setHeight(390);
            window.setModal(true);
            window.setOnItemClickListener(listener);
            return window;
        }
        return null;
    }

    public String getGroup(int index) {
        return groups.get(index);
    }

    public ArrayList<String> getGroups() {
        return new ArrayList<String>(groups);
    }

    public void addGroup(String group) {
        groups.add(group);
        write();
    }

    public void removeGroup(String group) {
        for (int i = groups.size() - 1; i >= 0; i--) {
            if (groups.get(i).equals(group)) {
                groups.remove(i);
            }
        }
        write();
    }

    private void write() {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(groupsLocation));
            for (String line: groups) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch (Exception e) { }
    }

    private GroupManager() {
    }
}
