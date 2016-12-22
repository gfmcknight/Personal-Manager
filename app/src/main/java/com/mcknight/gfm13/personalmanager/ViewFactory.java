package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcknight.gfm13.personalmanager.Groups.OnGroupRemovalListener;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEventType;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;

import java.util.Calendar;
import java.util.Date;

import static android.text.InputType.TYPE_CLASS_NUMBER;


/**
 * Created by gfm13 on 9/30/2016.
 */

public class ViewFactory {
    private static final int PADDING = 12;
    private static final int MARGIN = 2;
    private static final int TASK_LENGTH_CUTOFF = 425;

    public static DateChoiceHandler makeDateView(Context context) {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar.getInstance().setTime(currentDate);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return makeDateView(context, currentYear, currentMonth, currentDay);
    }

    public static DateChoiceHandler makeDateView(Context context, int year, int month, int day) {
        LinearLayout linearLayout = makeLinearLayoutWrapper(context);

        LinearLayout monthLayout = new LinearLayout(context);
        monthLayout.setOrientation(LinearLayout.VERTICAL);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            monthLayout.setLayoutParams(dimensions);
        }
        Button monthIncrement = new Button(context);
        monthIncrement.setText("^");
        Button monthDecrement = new Button(context);
        monthDecrement.setText("v");
        EditText monthText = new EditText(context);
        monthText.setInputType(TYPE_CLASS_NUMBER);
        monthText.setText(Integer.valueOf(month).toString());
        monthText.setMaxEms(2);
        monthLayout.addView(monthIncrement);
        monthLayout.addView(monthText);
        monthLayout.addView(monthDecrement);

        LinearLayout dayLayout = new LinearLayout(context);
        dayLayout.setOrientation(LinearLayout.VERTICAL);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            monthLayout.setLayoutParams(dimensions);
        }
        Button dayIncrement = new Button(context);
        dayIncrement.setText("^");
        Button dayDecrement = new Button(context);
        dayDecrement.setText("v");
        EditText dayText = new EditText(context);
        dayText.setInputType(TYPE_CLASS_NUMBER);
        dayText.setText(Integer.valueOf(day).toString());
        dayText.setMaxEms(2);
        dayLayout.addView(dayIncrement);
        dayLayout.addView(dayText);
        dayLayout.addView(dayDecrement);


        LinearLayout yearLayout = new LinearLayout(context);
        yearLayout.setOrientation(LinearLayout.VERTICAL);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
            yearLayout.setLayoutParams(dimensions);
        }
        Button yearIncrement = new Button(context);
        yearIncrement.setText("^");
        Button yearDecrement = new Button(context);
        yearDecrement.setText("v");
        EditText yearText = new EditText(context);
        yearText.setInputType(TYPE_CLASS_NUMBER);
        yearText.setText(Integer.valueOf(year).toString());
        yearText.setMaxEms(4);
        yearLayout.addView(yearIncrement);
        yearLayout.addView(yearText);
        yearLayout.addView(yearDecrement);

        linearLayout.addView(monthLayout);
        linearLayout.addView(dayLayout);
        linearLayout.addView(yearLayout);

        return new DateChoiceHandler(yearText, monthText, dayText, yearIncrement, yearDecrement, monthIncrement,
                monthDecrement, dayIncrement, dayDecrement, linearLayout);
    }

    public static View makeGroupItemView(final String name, Context context, final OnGroupRemovalListener listener){
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);
        linearLayout.setBackgroundResource(R.drawable.groupbackground);

        TextView groupName = new TextView(context);
        groupName.setText(name);
        groupName.setTextSize(18.0f);
        groupName.setTextColor(Color.BLACK);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            groupName.setLayoutParams(dimensions);
        }
        linearLayout.addView(groupName);

        Button removeButton = new Button(context);
        removeButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40 * MainActivity.DP_PIXEL_SCALING)));
        removeButton.setBackgroundResource(R.drawable.delete);
        linearLayout.addView(removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGroupRemoval(name, linearLayout);
            }
        });

        return linearLayout;
    }

    public static View makeGroupItemView(Context context, final OnGroupRemovalListener listener) {
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);
        linearLayout.setBackgroundResource(R.drawable.groupbackground);

        EditText groupName = new EditText(context);
        groupName.setHint("Name of this group...");
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            groupName.setLayoutParams(dimensions);
        }
        linearLayout.addView(groupName);

        Button removeButton = new Button(context);
        removeButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        removeButton.setBackgroundResource(R.drawable.delete);
        linearLayout.addView(removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGroupRemoval("", linearLayout);
            }
        });

        return linearLayout;
    }

    public static View makeTaskView(final Task task, final Context context)
    {
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);
        linearLayout.setBackgroundResource(R.drawable.taskbackground);

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
        taskSubject.setText(task.getName() + ": " + (Math.round(task.getHoursEstimate() * 10))/10 + "hrs");
        taskSubject.setTextSize(18.0f);
        taskSubject.setTextColor(Color.BLACK);

        String[] date = task.getDateDue().toString().split(" ");
        TextView taskDescription = new TextView(context);
        taskDescription.setText(date[0] + " " + date[1]+ " " + date[2] + "\n"+ task.getDescription());
        taskDescription.setTextSize(12.0f);
        taskDescription.setTextColor(Color.GRAY);

        textLayout.addView(taskSubject);
        textLayout.addView(taskDescription);

        LinearLayout buttonLayout;
        int taskLengthScore = (3 * task.getName().length()) + (2 * task.getDescription().length());
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
                (int)(40 * MainActivity.DP_PIXEL_SCALING)));
        buttonLayout.addView(startButton);

        Button editButton = new Button(context);
        editButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40 * MainActivity.DP_PIXEL_SCALING)));
        editButton.setBackgroundResource(R.drawable.edit);
        buttonLayout.addView(editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTask.class);
                context.startActivity(intent);
            }
        });

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        deleteButton.setBackgroundResource(R.drawable.delete);
        buttonLayout.addView(deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemManager.getTaskManager().removeItem(task);
                ItemManager.getTaskManager().commit();
                RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.DELETE, task));
            }
        });

        return linearLayout;
    }

    public static LinearLayout makeStepView(Context context, final LinearLayout parent) {
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);

        LinearLayout swapLayout = new LinearLayout(context);
        swapLayout.setVisibility(View.VISIBLE);
        swapLayout.setOrientation(LinearLayout.VERTICAL);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            swapLayout.setLayoutParams(dimensions);
        }
        Button swapUp = new Button(context);
        swapUp.setText("^");
        Button swapDown = new Button(context);
        swapDown.setText("v");
        swapLayout.addView(swapUp);
        swapLayout.addView(swapDown);

        swapUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newIndex = parent.indexOfChild(linearLayout) - 1;
                if (newIndex >= 0) {
                    parent.removeView(linearLayout);
                    parent.addView(linearLayout, newIndex);
                }
            }
        });

        swapDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newIndex = parent.indexOfChild(linearLayout) + 1;
                if (newIndex < parent.getChildCount()) {
                    parent.removeView(linearLayout);
                    parent.addView(linearLayout, newIndex);
                }
            }
        });

        EditText stepName = new EditText(context);
        {
            LinearLayout.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.75f);
            stepName.setLayoutParams(layoutParams);
        }

        EditText stepTime = new EditText(context);
        {
            LinearLayout.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f);
            stepTime.setLayoutParams(layoutParams);
        }
        stepTime.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(40 * MainActivity.DP_PIXEL_SCALING),
                (int)(40* MainActivity.DP_PIXEL_SCALING)));
        deleteButton.setBackgroundResource(R.drawable.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView(linearLayout);
            }
        });

        linearLayout.addView(swapLayout);
        linearLayout.addView(stepName);
        linearLayout.addView(stepTime);
        linearLayout.addView(deleteButton);
        return linearLayout;
    }

    private static LinearLayout makeLinearLayoutWrapper(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dimensions.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
            linearLayout.setLayoutParams(dimensions);
        }
        return linearLayout;
    }
}
