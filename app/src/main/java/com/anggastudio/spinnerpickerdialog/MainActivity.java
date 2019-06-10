package com.anggastudio.spinnerpickerdialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_YEAR = 18;
    EditText chooseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseDate = findViewById(R.id.etChooseDate);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.YEAR, -MAX_YEAR);
        Calendar settledCalendar = getSettledCalendar();

        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setCalendar(settledCalendar);
        spinnerPickerDialog.setMaxCalendar(maxCalendar);
        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        spinnerPickerDialog.setmTextColor(Color.BLACK);
        spinnerPickerDialog.setArrowButton(true);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {

            @Override
            public void onSetDate(int month, int day, int year) {
                String date = (month + 1) + "/" + day + "/" + year;
                chooseDate.setText(date);
                spinnerPickerDialog.dismiss();
            }

            @Override
            public void onCancel() {
                spinnerPickerDialog.dismiss();
            }

            @Override
            public void onDismiss() {
                chooseDate.clearFocus();
            }


        });
        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
    }

    /**
     * get settled calendar from text view
     *
     * @return settledCalendar : Calendar
     */
    private Calendar getSettledCalendar() {
        Calendar settledCalendar = null;
        String populatedDate = chooseDate.getText().toString();
        if (!populatedDate.isEmpty()) {
            String[] dateString = populatedDate.split("/");
            if (dateString.length > 0) {
                int month = Integer.parseInt(dateString[0]) - 1;
                int day = Integer.parseInt(dateString[1]);
                int year = Integer.parseInt(dateString[2]);
                settledCalendar = Calendar.getInstance();
                settledCalendar.set(year, month, day);
            }
        }
        return settledCalendar;
    }
}
