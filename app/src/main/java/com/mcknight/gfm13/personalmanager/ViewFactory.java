package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.mcknight.gfm13.personalmanager.WorkItems.Project;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;


/**
 * Created by gfm13 on 9/30/2016.
 */

public class ViewFactory {
    private static final int PADDING = 12;
    private static final int MARGIN = 2;
    private static final int TASK_LENGTH_CUTOFF = 200;
    private static final int ICON_SIZE = 30;

    private static Typeface TYPEFACE = Typeface.MONOSPACE;

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
        monthIncrement.setBackgroundResource(R.drawable.up);
        monthIncrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        Button monthDecrement = new Button(context);
        monthDecrement.setBackgroundResource(R.drawable.down);
        monthDecrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        EditText monthText = new EditText(context);

        monthText.setInputType(TYPE_CLASS_NUMBER);
        monthText.setText(Integer.valueOf(month).toString());
        monthText.setMaxEms(2);
        monthText.setTypeface(TYPEFACE);
        monthText.setTextColor(context.getColor(R.color.titleColor));
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
        dayIncrement.setBackgroundResource(R.drawable.up);
        dayIncrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        Button dayDecrement = new Button(context);
        dayDecrement.setBackgroundResource(R.drawable.down);
        dayDecrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        EditText dayText = new EditText(context);
        dayText.setInputType(TYPE_CLASS_NUMBER);
        dayText.setText(Integer.valueOf(day).toString());
        dayText.setMaxEms(2);
        dayText.setTypeface(TYPEFACE);
        dayText.setTextColor(context.getColor(R.color.titleColor));
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
        yearIncrement.setBackgroundResource(R.drawable.up);
        yearIncrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        Button yearDecrement = new Button(context);
        yearDecrement.setBackgroundResource(R.drawable.down);
        yearDecrement.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING) * 2,
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING * 0.5)));

        EditText yearText = new EditText(context);
        yearText.setInputType(TYPE_CLASS_NUMBER);
        yearText.setText(Integer.valueOf(year).toString());
        yearText.setMaxEms(4);
        yearText.setTypeface(TYPEFACE);
        yearText.setTextColor(context.getColor(R.color.titleColor));
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
        groupName.setTypeface(TYPEFACE);
        groupName.setTextSize(18.0f);
        groupName.setTextColor(context.getColor(R.color.textColor));
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            groupName.setLayoutParams(dimensions);
        }
        linearLayout.addView(groupName);

        Button removeButton = new Button(context);
        removeButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
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
        groupName.setTextColor(context.getColor(R.color.textColor));
        groupName.setTypeface(TYPEFACE);
        groupName.setHint("Name of this group...");
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            groupName.setLayoutParams(dimensions);
        }
        linearLayout.addView(groupName);

        Button removeButton = new Button(context);
        removeButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
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
        taskSubject.setTextColor(context.getColor(R.color.titleColor));
        taskSubject.setTypeface(TYPEFACE);

        String[] date = task.getDateDue().toString().split(" ");
        TextView taskDescription = new TextView(context);

        if (!task.getGroupName().equals("")) {
            taskDescription.setText(date[0] + " " + date[1]+ " " + date[2] + " for " + task.getGroupName() +
                    "\n"+ task.getDescription());
        } else {
            taskDescription.setText(date[0] + " " + date[1] + " " + date[2] + "\n" + task.getDescription());
        }
        taskDescription.setTextSize(12.0f);
        taskDescription.setTextColor(context.getColor(R.color.textColor));
        taskDescription.setTypeface(TYPEFACE);

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

        Button doneButton = new Button(context);
        doneButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        doneButton.setBackgroundResource(R.drawable.done);
        buttonLayout.addView(doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemManager.getTaskManager().removeItem(task);
                ItemManager.getTaskManager().commit();
                RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.FINISH, task));
            }
        });

        Button editButton = new Button(context);
        editButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        editButton.setBackgroundResource(R.drawable.edit);
        buttonLayout.addView(editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTask.class);
                intent.putExtra(context.getString(R.string.edit_ID), task.getId());
                context.startActivity(intent);
            }
        });

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
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

    public static LinearLayout makeProjectView(final Project project, final Context context) {
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.taskbackground);

        LinearLayout header = new LinearLayout(context);
        {
            LinearLayout.LayoutParams dimensions;
            dimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            header.setLayoutParams(dimensions);
        }

        LinearLayout titleHolder = new LinearLayout(context);
        titleHolder.setOrientation(LinearLayout.HORIZONTAL);
        {
            LinearLayout.LayoutParams dimensions = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            titleHolder.setLayoutParams(dimensions);
        }

        TextView title = new TextView(context);
        title.setText(project.getName() + " for " + project.getGroupName() + " " +
                Integer.valueOf((int)(project.getCompletion() * 100)).toString() + "%");
        title.setTextSize(18.0f);
        title.setTextColor(context.getColor(R.color.titleColor));
        title.setTypeface(TYPEFACE);
        titleHolder.addView(title);

        Button finishButton = new Button(context);
        finishButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        finishButton.setBackgroundResource(R.drawable.done);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemManager.getProjectManager().removeItem(project);
                ItemManager.getProjectManager().commit();
                RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.FINISH, project));
            }
        });

        Button editButton = new Button(context);
        editButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        editButton.setBackgroundResource(R.drawable.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProject.class);
                intent.putExtra(context.getString(R.string.edit_ID), project.getId());
                context.startActivity(intent);
            }
        });

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        deleteButton.setBackgroundResource(R.drawable.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemManager.getProjectManager().removeItem(project);
                ItemManager.getProjectManager().commit();
                RefreshInvoker.getInstance().invokeRefreshEvent(new RefreshEvent(RefreshEventType.DELETE, project));
            }
        });


        final Button showButton = new Button(context);
        showButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        showButton.setBackgroundResource(R.drawable.down);


        header.addView(titleHolder);
        header.addView(finishButton);
        header.addView(editButton);
        header.addView(deleteButton);
        header.addView(showButton);
        linearLayout.addView(header);

        final List<LinearLayout> stepViews = new ArrayList<>();
        final List<String> stepNames = project.getSteps();
        final List<Double> stepLengths = project.getTimeEstimates();
        final List<Boolean> stepsCompleted = project.getCompleted();

        for (int i = 0; i < stepNames.size(); i++) {
            LinearLayout stepLayout = new LinearLayout(context);
            {
                LinearLayout.LayoutParams dimensions = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                stepLayout.setLayoutParams(dimensions);
            }

            final Button completionButton = new Button(context);
            completionButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                    (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
            if (stepsCompleted.get(i)) {
                completionButton.setBackgroundResource(R.drawable.complete);
            } else {
                completionButton.setBackgroundResource(R.drawable.incomplete);
            }

            final int finalI = i;
            completionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    project.toggleCompletion(finalI);
                    if (stepsCompleted.get(finalI)) {
                        completionButton.setBackgroundResource(R.drawable.complete);
                    } else {
                        completionButton.setBackgroundResource(R.drawable.incomplete);
                    }
                    ItemManager.getProjectManager().commit();
                }
            });
            TextView stepDescription = new TextView(context);
            stepDescription.setText(stepNames.get(i) + ": " + (int)(double)(stepLengths.get(i)) + "hrs");
            stepDescription.setTextColor(context.getColor(R.color.textColor));
            stepDescription.setTypeface(TYPEFACE);

            stepLayout.addView(completionButton);
            stepLayout.addView(stepDescription);

            stepViews.add(stepLayout);
            linearLayout.addView(stepLayout);
            stepLayout.setVisibility(View.GONE);
        }

        final View.OnClickListener showAction;

        showAction = new View.OnClickListener() {
            boolean stepsShown = false;

            public void onClick(View view) {
                if (stepsShown) {
                    hideSteps();
                } else {
                    showSteps();
                }
                stepsShown = !stepsShown;
            }

            public void hideSteps() {
                showButton.setBackgroundResource(R.drawable.down);
                for (LinearLayout stepLayout: stepViews) {
                    stepLayout.setVisibility(View.GONE);
                }
            }

            public void showSteps() {
                showButton.setBackgroundResource(R.drawable.up);
                for (LinearLayout stepLayout: stepViews) {
                    stepLayout.setVisibility(View.VISIBLE);
                }
            }
        };

        showButton.setOnClickListener(showAction);

        return linearLayout;
    }

    public static LinearLayout makeStepView(Context context, final LinearLayout parent, String name, double time) {
        final LinearLayout linearLayout = makeLinearLayoutWrapper(context);

        Button swapUp = new Button(context);
        swapUp.setBackgroundResource(R.drawable.up);
        swapUp.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));

        Button swapDown = new Button(context);
        swapDown.setBackgroundResource(R.drawable.down);
        swapDown.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));;


        linearLayout.addView(swapUp);
        linearLayout.addView(swapDown);


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
        stepName.setText(name);
        stepName.setHint("Name this step");
        stepName.setTextColor(context.getColor(R.color.textColor));
        stepName.setTypeface(TYPEFACE);
        {
            LinearLayout.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.75f);
            stepName.setLayoutParams(layoutParams);
        }

        EditText stepTime = new EditText(context);
        if (time != 0) {
            stepTime.setText(Integer.valueOf((int)time).toString());
        }
        stepTime.setHint("Time");
        stepTime.setTextColor(context.getColor(R.color.textColor));
        stepTime.setTypeface(TYPEFACE);
        {
            LinearLayout.LayoutParams layoutParams;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f);
            stepTime.setLayoutParams(layoutParams);
        }
        stepTime.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button deleteButton = new Button(context);
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        deleteButton.setBackgroundResource(R.drawable.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView(linearLayout);
            }
        });

        linearLayout.addView(stepName);
        linearLayout.addView(stepTime);
        linearLayout.addView(deleteButton);
        return linearLayout;
    }

    public static LinearLayout makeStepView(Context context, final LinearLayout parent) {
        return (makeStepView(context, parent, "", 0));
    }

    public static LinearLayout makeAchievementView(Context context, String name, Boolean complete) {
        LinearLayout linearLayout = new LinearLayout(context);
        {
            LinearLayout.LayoutParams dimensions = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(dimensions);
        }

        Button completionImage = new Button(context);
        completionImage.setLayoutParams(new ViewGroup.LayoutParams((int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING),
                (int)(ICON_SIZE * MainActivity.DP_PIXEL_SCALING)));
        if (complete) {
            completionImage.setBackgroundResource(R.drawable.complete);
        } else {
            completionImage.setBackgroundResource(R.drawable.incomplete);
        }

        TextView achievementName = new TextView(context);
        achievementName.setText(name);
        achievementName.setTextColor(context.getColor(R.color.textColor));
        achievementName.setTypeface(TYPEFACE);

        linearLayout.addView(completionImage);
        linearLayout.addView(achievementName);

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
