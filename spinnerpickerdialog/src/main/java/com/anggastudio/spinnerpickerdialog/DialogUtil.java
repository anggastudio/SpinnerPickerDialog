package com.anggastudio.spinnerpickerdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class DialogUtil {

    public static Dialog setMargins(Dialog dialog, int marginLeft, int marginRight, Drawable dialogBackground) {
        Window window = dialog.getWindow();
        if (window == null) {
            // dialog window is not available, cannot apply margins
            return dialog;
        }
        Context context = dialog.getContext();

        // set dialog to fullscreen
        RelativeLayout root = new RelativeLayout(context);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.setBackgroundColor(Color.WHITE);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);

        // set background to get rid of additional margins
        Drawable background = dialogBackground == null ? new ColorDrawable(Color.WHITE) : dialogBackground;
        window.setBackgroundDrawable(background); // can be customized

        // apply left and top margin directly
        window.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = marginLeft;
        window.setAttributes(attributes);

        // set right and bottom margin implicitly by calculating width and height of dialog
        Point displaySize = getDisplayDimensions(context);
        int width = displaySize.x - marginLeft - marginRight;
        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    private static Point getDisplayDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // find out if status bar has already been subtracted from screenHeight
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(metrics);
        } else {
            display.getMetrics(metrics);
        }
        int physicalHeight = metrics.heightPixels;
        int statusBarHeight = getStatusBarHeight(context);
        int navigationBarHeight = getNavigationBarHeight(context);
        int heightDelta = physicalHeight - screenHeight;
        if (heightDelta == 0 || heightDelta == navigationBarHeight) {
            screenHeight -= statusBarHeight;
        }

        return new Point(screenWidth, screenHeight);
    }

    private static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return (resourceId > 0) ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    private static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return (resourceId > 0) ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    public static int getDialogMargin(int screenWidthOrHeight, float persen) {
        return (int) (screenWidthOrHeight * persen);
    }
}
