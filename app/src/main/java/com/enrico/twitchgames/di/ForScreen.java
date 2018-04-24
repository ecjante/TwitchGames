package com.enrico.twitchgames.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by enrico.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForScreen {
}
