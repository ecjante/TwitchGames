package com.enrico.twitchgames.screenshot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 */
public class ScreenshotController extends BaseController {

    static final String SCREENSHOT_KEY = "screenshot";

    public static Controller newInstance(String screenshot) {
        Bundle bundle = new Bundle();
        bundle.putString(SCREENSHOT_KEY, screenshot);
        return new ScreenshotController(bundle);
    }

    @Inject @Named("screenshot") String screenshot;
    @BindView(R.id.iv_screenshot) ImageView screenshotImage;

    public ScreenshotController(Bundle bundle) { super(bundle); }

    @Override
    protected void onViewBound(View view) {
        super.onViewBound(view);
        loadImage();
    }

    @Override
    protected void onActivityResumed(@NonNull Activity activity) {
        super.onActivityResumed(activity);
        loadImage();
    }

    private void loadImage() {
        Glide.with(screenshotImage.getContext())
                .load(screenshot)
                .apply(new RequestOptions().fitCenter())
                .into(screenshotImage);
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_screenshot;
    }
}
