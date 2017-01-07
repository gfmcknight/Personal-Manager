package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 1/6/2017.
 */

public class AchievementManager {
    Context context;

    List<JSONObject> achievements;

    public AchievementManager(Context context) {
        String[] rawAchievements = context.getResources().getStringArray(R.array.achievements);
        achievements = new ArrayList<>(rawAchievements.length);
        for (String s: rawAchievements) {
            try {
                achievements.add(new JSONObject(s));
            } catch (JSONException e) {
                String exceptionText = e.getMessage();
                int i = 0;
            }
        }
    }

    public String updateAchievements(int tasks, int projects, int hours, SharedPreferences preferences) throws JSONException {
        StringBuilder builder = new StringBuilder();

        SharedPreferences.Editor edit = preferences.edit();

        for (int i = 0; i < achievements.size(); i++) {
            if (!preferences.getBoolean("Achievement-" + i, false)) {
                JSONObject achievement = achievements.get(i);
                if (!achievement.getBoolean("SingleProject")) {
                    if (tasks >= achievement.getInt("MinTasks") && projects >= achievement.getInt("MinProjects") &&
                            hours >= achievement.getInt("MinTime")) {
                        builder.append(achievement.getString("Name"));
                        builder.append("!");
                        builder.append(achievement.getInt("Points"));
                        builder.append("!");
                        edit.putInt("Points", preferences.getInt("Points", 0) + achievement.getInt("Points"));
                        edit.putBoolean("Achievement-" + i, true);
                    }
                }
            }
        }

        edit.commit();
        return builder.toString();
    }

    public String updateAchievements(int tasks, int projects, int hours, int projectSteps, int projectHours,
                                     SharedPreferences preferences) throws JSONException {
        StringBuilder builder = new StringBuilder();

        SharedPreferences.Editor edit = preferences.edit();

        for (int i = 0; i < achievements.size(); i++) {
            if (!preferences.getBoolean("Achievement-" + i, false)) {
                JSONObject achievement = achievements.get(i);
                if (achievement.getBoolean("SingleProject")) {
                    if (projectSteps >= achievement.getInt("MinSteps") && projectHours >= achievement.getInt("MinLength")) {
                        builder.append(achievement.getString("Name"));
                        builder.append("!");
                        builder.append(achievement.getInt("Points"));
                        builder.append("!");
                        edit.putInt("Points", preferences.getInt("Points", 0) + achievement.getInt("Points"));
                        edit.putBoolean("Achievement-" + i, true);
                    }
                } else {
                    if (tasks >= achievement.getInt("MinTasks") && projects >= achievement.getInt("MinProjects") &&
                            hours >= achievement.getInt("MinTime")) {
                        builder.append(achievement.getString("Name"));
                        builder.append("!");
                        builder.append(achievement.getInt("Points"));
                        builder.append("!");
                        edit.putInt("Points", preferences.getInt("Points", 0) + achievement.getInt("Points"));
                        edit.putBoolean("Achievement-" + i, true);
                    }
                }
            }
        }

        edit.commit();
        return builder.toString();
    }
}
