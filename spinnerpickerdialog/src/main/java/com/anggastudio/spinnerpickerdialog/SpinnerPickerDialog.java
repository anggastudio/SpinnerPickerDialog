package com.anggastudio.spinnerpickerdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;

public class SpinnerPickerDialog extends DialogFragment implements View.OnClickListener {

    private static final int ARROW_UP = 0;
    private static final int ARROW_DOWN = 1;
    private Context mContext;
    private SpinnerPickerDialog.OnDialogListener mOnDialogListener;
    private OnDateSetPerValue onDateSetListener;
    private OnCancel onCancelListener;
    private OnDismiss onDismissListener;
    private OnDateSetMillis onDateSetMillis;
    private DatePicker datePicker;
    private int screenWidth;
    private Calendar maxCalendar;
    OnDateChangedListener onDateChangedListener = (view, year, monthOfYear, dayOfMonth) -> {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(year, monthOfYear, dayOfMonth);
    };
    private Calendar mCalendar;
    private int mTextColor;
    private int mDividerColor;
    private int titleColor;
    private int titleUnderlineColor;
    private String titleText;
    private int decreasedHeight;
    private int setButtonColor;
    private int cancelButtonColor;
    private Drawable dialogBackground;
    private Drawable arrowDrawable;
    private int pickersCount;
    private boolean isDefaultCalendar;
    private int arrowColorFilter = Color.GRAY;

    public SpinnerPickerDialog() { // default
        this.mCalendar = Calendar.getInstance();
        this.isDefaultCalendar = true;
        this.mTextColor = Color.BLACK;
        this.mDividerColor = Color.GRAY;
        this.titleText = "Set Date";
        this.titleColor = Color.BLACK;
        this.titleUnderlineColor = Color.BLACK;
        this.cancelButtonColor = Color.BLACK;
        this.setButtonColor = Color.BLACK;
    }

    public SpinnerPickerDialog setAllColor(int color) {
        this.mTextColor = color;
        this.mDividerColor = color;
        this.titleColor = color;
        this.titleUnderlineColor = color;
        this.cancelButtonColor = color;
        this.setButtonColor = color;
        return this;
    }

    public SpinnerPickerDialog setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public SpinnerPickerDialog setDialogBackground(Drawable background) {
        this.dialogBackground = background;
        return this;
    }

    public SpinnerPickerDialog setArrowButton(Drawable arrowDrawable) {
        this.arrowDrawable = arrowDrawable;
        return this;
    }

    public SpinnerPickerDialog setArrowButton(boolean isSetArrow) {
        if (isSetArrow) {
            this.arrowDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_drop_up);
        }
        return this;
    }

    public SpinnerPickerDialog setMaxCalendar(Calendar maxCalendar) {
        this.maxCalendar = maxCalendar;
        this.mCalendar = isDefaultCalendar ? maxCalendar : mCalendar;
        return this;
    }

    public SpinnerPickerDialog setCalendar(Calendar settledCalendar) {
        if (settledCalendar != null) {
            this.mCalendar = settledCalendar;
            this.isDefaultCalendar = false;
        } else if (maxCalendar != null) {
            this.mCalendar = maxCalendar;
            this.isDefaultCalendar = false;
        }
        return this;
    }

    public SpinnerPickerDialog setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        return this;
    }

    public SpinnerPickerDialog setmDividerColor(int mDividerColor) {
        this.mDividerColor = mDividerColor;
        return this;
    }

    public SpinnerPickerDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public SpinnerPickerDialog setCancelButtonColor(int cancelButtonColor) {
        this.cancelButtonColor = cancelButtonColor;
        return this;
    }

    public SpinnerPickerDialog setSetButtonColor(int setButtonColor) {
        this.setButtonColor = setButtonColor;
        return this;
    }

    public SpinnerPickerDialog setTitleUnderlineColor(int titleUnderlineColor) {
        this.titleUnderlineColor = titleUnderlineColor;
        return this;
    }

    public SpinnerPickerDialog setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public void setArrowColorFilter(int arrowColorFilter) {
        this.arrowColorFilter = arrowColorFilter;
    }

    /**
     * Set the dialog callback listener
     *
     * @param aOnDialogListener instance of OnDialogListener
     */
    public SpinnerPickerDialog setOnDialogListener(final OnDialogListener aOnDialogListener) {
        this.mOnDialogListener = aOnDialogListener;
        return this;
    }

    /**
     * Set the dialog callback listener
     *
     * @param onDateSetListener instance of OnDialogListener
     */
    public SpinnerPickerDialog setOnDateSetListener(final OnDateSetPerValue onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
        return this;
    }

    /**
     * Set the dialog callback listener
     *
     * @param onDateSetMillis instance of OnDialogListener
     */
    public SpinnerPickerDialog setOnDateSetListener(final OnDateSetMillis onDateSetMillis) {
        this.onDateSetMillis = onDateSetMillis;
        return this;
    }

    /**
     * Set the dialog callback listener
     *
     * @param onDismissListener instance of OnDismiss
     */
    public SpinnerPickerDialog setOnDismissListener(final OnDismiss onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    /**
     * Set the dialog callback listener
     *
     * @param onCancelListener instance of OnCancel
     */
    public SpinnerPickerDialog setOnCancelListener(final OnCancel onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create dialog in an arbitrary way
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        if (mContext != null) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                Display display = wm.getDefaultDisplay();

                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                screenWidth = metrics.widthPixels;
                float marginScale = 0.15f;
                if (screenWidth > 720) {
                    marginScale = 1.6f * marginScale;
                }
                int margin = getWidthByPersen(marginScale);
                if (dialogBackground == null) {
                    dialogBackground = ContextCompat.getDrawable(mContext, R.drawable.background_white_radius_4dp);
                }
                DialogUtil.setMargins(dialog, margin, margin, dialogBackground);
            }
        }

        return dialog;
    }

    private int getWidthByPersen(float persen) {
        return (int) (screenWidth * persen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spinner_datepicker_layout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.dialogTitle);
        View underline = view.findViewById(R.id.titleUnderline);
        datePicker = view.findViewById(R.id.spinnerDatePicker);
        TextView setButton = view.findViewById(R.id.setDateButton);
        TextView cancelButton = view.findViewById(R.id.cancelButton);

        setButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        initDatePicker(datePicker);
        customizeTitleSection(title, underline);
        customizeFooterSection(cancelButton, setButton);
        customizeDatePicker(datePicker);
    }

    private void customizeFooterSection(TextView cancelButton, TextView setButton) {
        cancelButton.setTextColor(cancelButtonColor);
        setButton.setTextColor(setButtonColor);
    }

    private void customizeTitleSection(TextView title, View underline) {
        title.setText(titleText);
        title.setTextColor(titleColor);
        underline.setBackgroundColor(titleUnderlineColor);
    }

    private void initDatePicker(DatePicker datePicker) {
        //set date changed listener without timeout / async task will potentially causing stack overflow error.
        if (maxCalendar != null) {
            datePicker.setMaxDate(maxCalendar.getTimeInMillis());
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), onDateChangedListener);
        } else {
            datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), null);
        }
    }

    private void customizeDatePicker(DatePicker datePicker) {
        // set date picker padding
        int pTop = datePicker.getPaddingTop();
        int pBottom = datePicker.getPaddingBottom();
        int datePickerPadding = getWidthByPersen(0.02f);
        datePicker.setPadding(datePickerPadding, pTop, datePickerPadding, pBottom);

        // get each number picker
        LinearLayout pickerRootLayout = (LinearLayout) datePicker.getChildAt(0);
        pickerRootLayout.setGravity(Gravity.CENTER);
        LinearLayout pickersLayout = (LinearLayout) pickerRootLayout.getChildAt(0);
        pickersLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        pickersLayout.setLayoutParams(layoutParams);

        // set layout params for each number picker
        LinearLayout.LayoutParams pickerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        int marginHorizontal = getWidthByPersen(0.035f);
        int marginVertical = getWidthByPersen(0.03f);
        pickerLayoutParams.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);

        pickersCount = pickersLayout.getChildCount();
        for (int i = 0; i < pickersCount; i++) {
            pickersLayout.setGravity(Gravity.CENTER);
            NumberPicker picker = (NumberPicker) pickersLayout.getChildAt(i);
            setNumberPickerText(picker, mTextColor);
            setNumberPickerDividerColor(picker, mDividerColor);
            picker.setGravity(Gravity.CENTER);
            picker.setLayoutParams(pickerLayoutParams);
            picker.setDividerPadding(getWidthByPersen(-0.015f));
        }

        pickersLayout.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int pickerHeight = pickersLayout.getMeasuredHeight();
        if (pickerHeight < 300) pickerHeight = 350;
        decreasedHeight = (int) (pickerHeight * 0.8f);

        if (mContext != null) {
            addArrowButtons(pickerRootLayout, pickersLayout);
        }
    }

    private void addArrowButtons(LinearLayout pickerRootLayout, LinearLayout pickersLayout) {
        LinearLayout arrowButtonsUp = getArrowButtonsLayout(ARROW_UP, pickersCount); // up
        LinearLayout arrowButtonsDown = getArrowButtonsLayout(ARROW_DOWN, pickersCount); // down

        pickerRootLayout.removeAllViews();
        pickerRootLayout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout.LayoutParams relativeParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, decreasedHeight);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setLayoutParams(relativeParam);

        pickerRootLayout.addView(relativeLayout, 0);

        relativeLayout.addView(pickersLayout, 0);
        relativeLayout.addView(arrowButtonsUp, 1);
        relativeLayout.addView(arrowButtonsDown, 2);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) arrowButtonsDown.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        arrowButtonsDown.setLayoutParams(params);
        arrowButtonsDown.setPadding(0, 0, 0, getWidthByPersen(0.01f));
        arrowButtonsUp.setPadding(0, getWidthByPersen(0.01f), 0, 0);

    }

    private LinearLayout getArrowButtonsLayout(int arrowDirection, int count) {
        LinearLayout.LayoutParams arrowLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout arrowButtonsLayout = new LinearLayout(mContext);
        arrowButtonsLayout.setLayoutParams(arrowLayoutParam);

        for (int i = 0; i < count; i++) {
            LinearLayout arrowButton = getArrowButton(arrowDirection);
            arrowButtonsLayout.addView(arrowButton, i);
        }

        return arrowButtonsLayout;
    }

    private LinearLayout getArrowButton(int arrowDirection) {
        LinearLayout.LayoutParams arrowButtonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout arrowButton = new LinearLayout(mContext);
        arrowButton.setLayoutParams(arrowButtonParam);
        arrowButton.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams arrowParam = new LinearLayout.LayoutParams(getWidthByPersen(0.03f), getWidthByPersen(0.03f));
        ImageView arrowImage = new ImageView(mContext);
        arrowImage.setLayoutParams(arrowParam);
        if (this.arrowDrawable != null) {
            arrowImage.setImageDrawable(arrowDrawable);
            arrowImage.setColorFilter(this.arrowColorFilter);
            arrowImage.setAlpha(0.5f);
        } else {
            arrowImage.setVisibility(View.GONE);
        }

        if (arrowDirection == ARROW_DOWN) { // down
            arrowImage.setRotation(180f);
        }

        arrowButton.addView(arrowImage);
        return arrowButton;
    }

    private void setNumberPickerText(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    child.setEnabled(false);
                    child.setFocusableInTouchMode(false);
                    numberPicker.invalidate();
                } catch (Exception e) {
                    Log.w("setPickerText", e);
                }
            }
        }
    }

    public void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
                Field dividerFieldDistance = numberPicker.getClass().getDeclaredField("mSelectionDividersDistance");
                dividerField.setAccessible(true);
                dividerFieldDistance.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(color);
                dividerField.set(numberPicker, colorDrawable);
                dividerFieldDistance.set(numberPicker, 72);
                numberPicker.invalidate();
            } catch (Exception e) {
                Log.w("PickerDividerColor", e);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.setDateButton) {
            setDate();
        } else if (viewId == R.id.cancelButton) {
            cancel();
        }
    }

    /**
     * Send positive button click event to call back listener
     */
    private void setDate() {
        if (mOnDialogListener != null) {
            mOnDialogListener.onSetDate(datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear());
        }
        if (onDateSetListener != null) {
            onDateSetListener.onSet(datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear());
        }
        if (onDateSetMillis != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            long millis = calendar.getTimeInMillis();
            onDateSetMillis.onSet(millis);
        }
        this.dismiss();
    }

    /**
     * Send negative button click event to call back listener
     */
    private void cancel() {
        doOnCancel();
        this.dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnDialogListener != null) {
            mOnDialogListener.onDismiss();
        }
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        doOnCancel();
    }

    private void doOnCancel() {
        if (mOnDialogListener != null) {
            mOnDialogListener.onCancel();
        }
        if (onCancelListener != null) {
            onCancelListener.onCancel();
        }
    }

    /**
     * Listener that wants to listen for dialog button events.
     */
    public interface OnDialogListener {

        /**
         * Called when set button clicked.
         */
        void onSetDate(int month, int day, int year);

        /**
         * Called when cancel button click
         */
        void onCancel();

        /**
         * Called when dialog is dismissing
         */
        void onDismiss();

    }

    /**
     * Listener that wants to listen for dialog button events.
     */
    public interface OnDateSetPerValue {

        /**
         * Called when set button clicked.
         */
        void onSet(int month, int day, int year);

    }

    /**
     * Listener that wants to listen for dialog button events.
     */
    public interface OnDateSetMillis {

        /**
         * Called when set button clicked.
         */
        void onSet(long millis);

    }

    /**
     * Listener that wants to listen for dialog button events.
     */
    public interface OnCancel {

        /**
         * Called when set button clicked.
         */
        void onCancel();

    }

    /**
     * Listener that wants to listen for dialog button events.
     */
    public interface OnDismiss {

        /**
         * Called when set button clicked.
         */
        void onDismiss();

    }
}
