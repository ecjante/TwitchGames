package com.enrico.twitchgames.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by enrico.
 *
 * Screen level scope
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScreenScope {
}
