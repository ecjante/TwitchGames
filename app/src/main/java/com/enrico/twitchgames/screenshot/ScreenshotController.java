package com.enrico.twitchgames.screenshot;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.base.BaseController;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by enrico.
 *
 * Controller for Screenshot screen
 */
public class ScreenshotController extends BaseController implements RequestListener<Drawable> {

    static final String SCREENSHOT_KEY = "screenshot";

    // Add the screenshot url to the bundle for the instance
    public static Controller newInstance(String screenshot) {
        Bundle bundle = new Bundle();
        bundle.putString(SCREENSHOT_KEY, screenshot);
        return new ScreenshotController(bundle);
    }

    @Inject @Named("screenshot") String screenshot;
    @BindView(R.id.iv_screenshot) ImageView screenshotImage;
    @BindView(R.id.loading_indicator) View loadingView;

    public ScreenshotController(Bundle bundle) { super(bundle); }

    @Override
    protected void onViewBound(View view) {
        super.onViewBound(view);
        // just load the image on view bound.
        loadImage();
    }

    @Override
    protected void onActivityResumed(@NonNull Activity activity) {
        super.onActivityResumed(activity);
        // load the image on activity resumed. mainly for screen rotation
        loadImage();
    }

    private void loadImage() {
        loadingView.setVisibility(View.VISIBLE);
        Glide.with(screenshotImage.getContext())
                .load(screenshot)
                .listener(this)
                .apply(new RequestOptions().fitCenter())
                .into(screenshotImage);
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_screenshot;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
        loadingView.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        loadingView.setVisibility(View.GONE);
        return false;
    }
}
