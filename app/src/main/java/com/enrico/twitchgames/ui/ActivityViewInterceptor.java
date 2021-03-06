package com.enrico.twitchgames.ui;

import android.app.Activity;
import android.support.annotation.LayoutRes;

/**
 * Created by enrico on 3/14/18.
 *
 * Used to intercept setContentView in order to wrap the view with a view that has a debug drawer
 */

public interface ActivityViewInterceptor {

    void setContentView(Activity activity, @LayoutRes int layoutRes);

    void clear();

    ActivityViewInterceptor DEFAULT = new ActivityViewInterceptor() {
        @Override
        public void setContentView(Activity activity, int layoutRes) {
            activity.setContentView(layoutRes);
        }

        @Override
        public void clear() {

        }
    };
}
