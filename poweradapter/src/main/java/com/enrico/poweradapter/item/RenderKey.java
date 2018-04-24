package com.enrico.poweradapter.item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by enrico.
 */
@MapKey
@Target(ElementType.METHOD)
public @interface RenderKey {

    String value();
}
