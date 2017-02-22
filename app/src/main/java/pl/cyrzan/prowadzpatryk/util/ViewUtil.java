package pl.cyrzan.prowadzpatryk.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Patryk on 09.02.2017.
 */

public final class ViewUtil {

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static int getNavBarHeight(Context context, @DimenRes int defaultRes) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId > 0 ? resourceId : defaultRes);
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    public static int getStatusBarHeight(Context context, @DimenRes int defaultRes) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId > 0 ? resourceId : defaultRes);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static void makeToast(Context context, String message) {
        if (context == null)
            return;

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

        toast.setText(message);
        toast.setGravity(android.view.Gravity.CENTER, 0, 0);
        toast.show();
    }
}
