package com.mcknight.gfm13.personalmanager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 9/15/2016.
 */
public class JSONConverter {

    private File source;

    public JSONConverter (File source){
        this.source = source;
    }

    public List<JSONObject> GetJSONObjects (){

        List<String> lines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            String lastLine;
            while ((lastLine = reader.readLine()) != null){
                if (lastLine == ""){
                    break;
                }
                lines.add(lastLine);
            }
            reader.close();
        }
        catch (Exception e){
            return new ArrayList<>();
        }

        List<JSONObject> objects = new ArrayList<>();

        for (String line : lines) {
            try {
                objects.add(new JSONObject(line));
            }
            catch (Exception e){ }//TODO: handle json loading exceptions
        }

        return objects;
    }

    public void PutJSONObjects(List<JSONObject> objects){
        List<String> lines = new ArrayList<>();

        for (JSONObject object: objects) {
            lines.add(object.toString());
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(source));
            for (String line: lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch (Exception e) { }
    }

}
