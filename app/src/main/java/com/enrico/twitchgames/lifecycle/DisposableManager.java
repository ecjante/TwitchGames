package com.enrico.twitchgames.lifecycle;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 */
public class DisposableManager {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void add(Disposable... disposables) {
        compositeDisposable.addAll(disposables);
    }

    public void dispose() {
        compositeDisposable.clear();
    }
}
