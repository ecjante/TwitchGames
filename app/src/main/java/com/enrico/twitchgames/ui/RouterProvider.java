package com.enrico.twitchgames.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;

/**
 * Created by enrico.
 */
public interface RouterProvider {

    Router getRouter();

    Controller initialScreen();
}
