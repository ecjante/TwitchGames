package com.enrico.twitchgames.details;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enrico.poweradapter.item.ItemRenderer;
import com.enrico.twitchgames.R;
import com.enrico.twitchgames.models.igdb.IgdbGameScreenshot;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by enrico.
 */
public class IgdbGameScreenshotRenderer implements ItemRenderer<IgdbGameScreenshot> {

    private final Provider<GameDetailsPresenter> presenterProvider;

    @Inject
    IgdbGameScreenshotRenderer(Provider<GameDetailsPresenter> presenterProvider) {
        this.presenterProvider = presenterProvider;
    }

    @Override
    public int layoutRes() {
        return R.layout.view_screenshot_list_item;
    }

    @Override
    public View createView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes(), parent, false);
        view.setTag(new ViewBinder(view, presenterProvider.get()));
        return view;
    }

    @Override
    public void render(@NonNull View itemView, @NonNull IgdbGameScreenshot item) {
        ((ViewBinder) itemView.getTag()).bind(item);
    }

    static class ViewBinder {

        @BindView(R.id.iv_screenshot) ImageView screenshotImage;
        private IgdbGameScreenshot screenshot;

        ViewBinder(View itemView, GameDetailsPresenter presenter) {
            ButterKnife.bind(this, itemView);
            screenshotImage.setOnClickListener(v -> {
                if (screenshot != null) {
                    presenter.onScreenshotClicked(screenshot.big());
                }
            });
        }

        void bind(IgdbGameScreenshot screenshot) {
            if (this.screenshot == null || !this.screenshot.equals(screenshot)) {
                this.screenshot = screenshot;
                Glide.with(screenshotImage.getContext())
                        .load(screenshot.medium())
                        .into(screenshotImage);
            }
        }
    }
}
