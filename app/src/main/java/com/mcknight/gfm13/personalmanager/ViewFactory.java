package com.mcknight.gfm13.personalmanager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by gfm13 on 9/30/2016.
 */

public class ViewFactory {
    private static final int PADDING = 12;
    private static final int MARGIN = 2;
    private static final int TASK_LENGTH_CUTOFF = 425;


    public static View makeView(final Task task, Context context)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundResource(R.drawable.taskbackground);
        linearLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dimensions.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
            linearLayout.setLayoutParams(dimensions);
        }

        LinearLayout textLayout = new LinearLayout(context);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textLayout.setLayoutParams(dimensions);
        }
        textLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textLayout);

        TextView taskSubject = new TextView(context);
        taskSubject.setText(task.Name);
        taskSubject.setTextSize(18.0f);
        taskSubject.setTextColor(Color.BLACK);

        TextView taskDescription = new TextView(context);
        taskDescription.setText(task.Description);
        taskDescription.setTextSize(12.0f);
        taskDescription.setTextColor(Color.GRAY);

        textLayout.addView(taskSubject);
        textLayout.addView(taskDescription);

        LinearLayout buttonLayout;
        int taskLengthScore = (3 * task.Name.length()) + (2 * task.Description.length());
        if (taskLengthScore < TASK_LENGTH_CUTOFF) {
            buttonLayout = linearLayout;
        } else {
            buttonLayout = new LinearLayout(context);
            buttonLayout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            buttonLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
            linearLayout.addView(buttonLayout);
        }

        Button startButton = new Button(context);
        startButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        buttonLayout.addView(startButton);

        Button editButton = new Button(context);
        editButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        editButton.setBackgroundResource(R.drawable.edit);
        buttonLayout.addView(editButton);

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        deleteButton.setBackgroundResource(R.drawable.delete);
        buttonLayout.addView(deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskManager.getInstance().RemoveTask(task);
                TaskManager.getInstance().Commit();
            }
        });

        return linearLayout;
    }
}
