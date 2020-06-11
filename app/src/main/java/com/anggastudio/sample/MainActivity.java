package com.anggastudio.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_YEAR = 18;
    EditText etChooseDate;
    TextView tvMonth;
    TextView tvDay;
    TextView tvYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etChooseDate = findViewById(R.id.etChooseDate);
        tvMonth = findViewById(R.id.tvMonth);
        tvDay = findViewById(R.id.tvDay);
        tvYear = findViewById(R.id.tvYear);

        etChooseDate.setOnClickListener(v -> showDatePickerDialog());

    }

    private void showDatePickerDialog() {
//        SpinnerPickerDialog spinnerPickerDialog = getGreenPickerDialog();
        SpinnerPickerDialog spinnerPickerDialog = getDefaultPickerDialog();
        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
    }

    private SpinnerPickerDialog getDefaultPickerDialog() {
        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {

            @Override
            public void onSetDate(int month, int day, int year) {
                // "  (Month selected is 0 indexed {0 == January})"
                String date = (month + 1) + "/" + day + "/" + year;
                etChooseDate.setText(date);

                String monthString = month + "  (Month selected is 0 indexed {0 == January})";
                tvMonth.setText(monthString);
                tvDay.setText(day + "");
                tvYear.setText(year + "");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {
                etChooseDate.clearFocus();
            }


        });
        return spinnerPickerDialog;

    }

    private SpinnerPickerDialog getGreenPickerDialog() {
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
        spinnerPickerDialog.setOnDateSetListener((month, day, year) -> {
            String date = (month + 1) + "/" + day + "/" + year;
            etChooseDate.setText(date);
            spinnerPickerDialog.dismiss();

            String monthString = month + "  (Month selected is 0 indexed {0 == January})";
            tvMonth.setText(monthString);
            tvDay.setText(day + "");
            tvYear.setText(year + "");
        });
        spinnerPickerDialog.setOnCancelListener(spinnerPickerDialog::dismiss);
        spinnerPickerDialog.setOnDismissListener(() -> etChooseDate.clearFocus());
        return spinnerPickerDialog;
    }

    /**
     * get settled calendar from text view
     *
     * @return settledCalendar : Calendar
     */
    private Calendar getSettledCalendar() {
        Calendar settledCalendar = null;
        String populatedDate = etChooseDate.getText().toString();
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
