package com.enrico.twitchgames.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.details.GameDetailsController;
import com.enrico.twitchgames.di.ActivityScope;
import com.enrico.twitchgames.lifecycle.ActivityLifecycleTask;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.screenshot.ScreenshotController;
import com.enrico.twitchgames.ui.changehandlers.ArcFadeMoveChangeHandlerCompat;
import com.enrico.twitchgames.youtube.QuickPlayActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by enrico.
 */
@ActivityScope
public class DefaultScreenNavigator extends ActivityLifecycleTask implements ScreenNavigator {

    private final Context context;
    private Router router;

    @Inject
    DefaultScreenNavigator(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(AppCompatActivity activity) {
        if (!(activity instanceof RouterProvider)) {
            throw new IllegalArgumentException("Activity must be instance of RouterProvider");
        }
        initWithRouter(((RouterProvider) activity).getRouter(), ((RouterProvider) activity).initialScreen());
    }

    void initWithRouter(Router router, Controller rootScreen) {
        this.router = router;
        if (!router.hasRootController())
            router.setRoot(RouterTransaction.with(rootScreen));
    }

    @Override
    public boolean pop() {
        return router != null && router.handleBack();
    }

    @Override
    public void goToGameDetails(long twitchGameId, String gameName, String gameBoxTemplate) {
        if (router != null) {
            router.pushController(
                    RouterTransaction.with(GameDetailsController.newInstance(twitchGameId, gameName, gameBoxTemplate))
                            .pushChangeHandler(new FadeChangeHandler())
                            .popChangeHandler(new FadeChangeHandler())

            );
        }
    }

    @Override
    public void openStream(TwitchStream stream) {
        Uri uri;
        if (isPackageInstalled()) {
            uri = Uri.parse(context.getString(R.string.twitch_app_uri) + stream.channel().name());
        } else {
            uri = Uri.parse(context.getString(R.string.twitch_browser_uri) + stream.channel().name());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    private boolean isPackageInstalled() {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(context.getString(R.string.twitch_package), PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void playVideo(String id) {
        Intent intent = new Intent(context, QuickPlayActivity.class);
        intent.putExtra(QuickPlayActivity.YOUTUBE_ID_KEY, id);
        context.startActivity(intent);
    }

    @Override
    public void openScreenshot(String url) {
        if (router != null) {
            String sharedName = context.getString(R.string.screenshot_image);
            List<String> names = new ArrayList<>();
            names.add(sharedName);
            router.pushController(
                    RouterTransaction.with(ScreenshotController.newInstance(url))
                            .pushChangeHandler(new ArcFadeMoveChangeHandlerCompat(sharedName))
                            .popChangeHandler(new ArcFadeMoveChangeHandlerCompat(sharedName))
            );
        }
    }

    @Override
    public void onDestroy(AppCompatActivity activity) {
        router = null;
    }
}
