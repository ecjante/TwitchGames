package com.enrico.twitchgames.ui.changehandlers;

import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;

public class ArcFadeMoveChangeHandlerCompat extends TransitionChangeHandlerCompat {

    public ArcFadeMoveChangeHandlerCompat() {
        super();
    }

    public ArcFadeMoveChangeHandlerCompat(String... transitionNames) {
        super(new ArcFadeMoveChangeHandler(transitionNames), new FadeChangeHandler());
    }

}
