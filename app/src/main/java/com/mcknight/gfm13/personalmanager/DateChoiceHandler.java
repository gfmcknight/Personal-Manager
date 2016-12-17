package com.mcknight.gfm13.personalmanager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gfm13 on 12/13/2016.
 */

public class DateChoiceHandler implements View.OnFocusChangeListener {

    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;

    private EditText[] dateComponents;
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private LinearLayout layout;

    public DateChoiceHandler(EditText yearText, EditText monthText, EditText dayText,
                             Button yearIncrement, Button yearDecrement, Button monthIncrement,
                             Button monthDecrement, Button dayIncrement, Button dayDecrement,
                             LinearLayout layout)
    {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar.getInstance().setTime(currentDate);

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        dateComponents = new EditText[3];
        dateComponents[YEAR_INDEX] = yearText;
        dateComponents[MONTH_INDEX] = monthText;
        dateComponents[DAY_INDEX] = dayText;

        yearIncrement.setOnClickListener(new DateIncrementListener(0, 1));
        yearDecrement.setOnClickListener(new DateIncrementListener(0, -1));
        monthIncrement.setOnClickListener(new DateIncrementListener(1, 1));
        monthDecrement.setOnClickListener(new DateIncrementListener(1, -1));
        dayIncrement.setOnClickListener(new DateIncrementListener(2, 1));
        dayDecrement.setOnClickListener(new DateIncrementListener(2, -1));

        yearText.setOnFocusChangeListener(this);
        monthText.setOnFocusChangeListener(this);
        dayText.setOnFocusChangeListener(this);

        this.layout = layout;
    }

    private void updateValuesAndButtons() {
        int yearValue = getComponentValue(YEAR_INDEX);
        int monthValue = getComponentValue(MONTH_INDEX);
        int dayValue = getComponentValue(DAY_INDEX);

        if (monthValue <= 0) {
            monthValue = 12;
        } else if (monthValue > 12) {
            monthValue = 1;
        }

        if (dayValue <= 0) {
            dayValue = getMaximumDay(monthValue, yearValue);
        } else if (dayValue > getMaximumDay(monthValue, yearValue)) {
            dayValue = 1;
        }

        if (yearValue <= currentYear) {
            yearValue = currentYear;
            if (monthValue <= currentMonth) {
                monthValue = currentMonth;
                if (dayValue <= currentDay) {
                    dayValue = currentDay;
                }
            }
        }

        dateComponents[YEAR_INDEX].setText(Integer.valueOf(yearValue).toString());
        dateComponents[MONTH_INDEX].setText(Integer.valueOf(monthValue).toString());
        dateComponents[DAY_INDEX].setText(Integer.valueOf(dayValue).toString());
    }

    private int getComponentValue(int index) {
        String componentText = dateComponents[index].getText().toString();
        int componentValue = 0;
        try {
            componentValue = Integer.parseInt(componentText);
        } catch (NumberFormatException e) {
            componentValue = 0;
        }
        return componentValue;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    private static int getMaximumDay(int month, int year) {
        switch (month) {
            case 1: return 31;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    return 29;
                }
                else {
                    return 28;
                }
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return 31;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus){
            updateValuesAndButtons();
        }
    }

    private class DateIncrementListener implements android.view.View.OnClickListener {
        int index;
        int increment;

        public DateIncrementListener(int index, int increment) {
            this.index = index;
            this.increment = increment;
        }

        public  void onClick(View view) {
            int componentValue = getComponentValue(index);
            componentValue += increment;
            dateComponents[index].setText(Integer.valueOf(componentValue).toString());
            updateValuesAndButtons();
        }
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public int getYear() {
        return getComponentValue(YEAR_INDEX);
    }

    public int getMonth() {
        return getComponentValue(MONTH_INDEX) - 1;
    }

    public int getDay() {
        return getComponentValue(DAY_INDEX);
    }
}
