package com.enrico.twitchgames.util;

import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by enrico.
 */
public class ButterknifeUtils {

    private ButterknifeUtils() {

    }

    public static void unbind(Unbinder unbinder) {
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (IllegalStateException e) {
                Timber.e("Error unbinding views");
            }
        }
    }
}
