package com.bkozyrev.tinkoffproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by boriskozyrev on 16.03.17.
 */

public class KeyboardUtils {

    public static void HideKeyboard(Activity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
