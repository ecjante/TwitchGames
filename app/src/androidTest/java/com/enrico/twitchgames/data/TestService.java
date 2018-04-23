package com.enrico.twitchgames.data;

import android.os.Handler;
import android.os.Looper;

import com.enrico.twitchgames.test.TestUtils;

import io.reactivex.Single;

/**
 * Created by enrico.
 */
public class TestService {

    private int errorFlags;
    private int holdFlags;

    final TestUtils testUtils;

    TestService(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    boolean noError(int flag) {
        return (errorFlags & flag) == 0;
    }

    boolean isHolding(int flag) {
        return (holdFlags & flag) == flag;
    }

    <T> Single<T> holdingSingle(T result, int flag) {
        return Single.create(e -> {
            final Handler handler = new Handler(Looper.getMainLooper());
            Runnable holdRunnable = new Runnable() {
                @Override
                public void run() {
                    if ((holdFlags & flag) == flag) {
                        handler.postDelayed(this, 50);
                    } else {
                        e.onSuccess(result);
                    }
                }
            };
            holdRunnable.run();
        });
    }

    public void setErrorFlags(int errorFlags) {
        this.errorFlags = errorFlags;
    }

    public void clearErrorFlags() {
        this.errorFlags = 0;
    }

    public void setHoldFlags(int holdFlags) {
        this.holdFlags = holdFlags;
    }

    public void clearHoldFlags() {
        this.holdFlags = 0;
    }
}
