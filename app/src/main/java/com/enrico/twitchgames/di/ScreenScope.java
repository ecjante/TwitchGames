package com.enrico.twitchgames.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by enrico.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScreenScope {
}
