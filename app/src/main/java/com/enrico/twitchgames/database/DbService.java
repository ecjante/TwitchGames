package com.enrico.twitchgames.database;

import android.annotation.SuppressLint;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by enrico.
 */
public class DbService {

    @SuppressLint("CheckResult")
    public void runDbOp(Action action) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {}, throwable -> Timber.e(throwable, "Error performing database operation"));
    }
}
