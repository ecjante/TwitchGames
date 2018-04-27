package com.enrico.twitchgames.details;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.di.ScreenScope;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.jakewharton.rxrelay2.BehaviorRelay;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by enrico.
 */
@ScreenScope
public class GameDetailsViewModel {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    private final BehaviorRelay<GameDetailState> detailStateRelay = BehaviorRelay.create();
    private final BehaviorRelay<StreamsState> streamsStateRelay = BehaviorRelay.create();

    @Inject
    GameDetailsViewModel() {
        // tell details and streams relay to start loading
        detailStateRelay.accept(GameDetailState.builder().loading(true).build());
        streamsStateRelay.accept(StreamsState.builder().loading(true).build());
    }

    // Observables for the screen

    Observable<GameDetailState> details() {
        return detailStateRelay;
    }

    Observable<StreamsState> streams() {
        return streamsStateRelay;
    }

    /**
     * Consumer to process the IGDB game and pass it to the relay
     * @return
     */
    Consumer<IgdbGame> processIgdbGame() {
        return igdbGame -> {
            String screenshot = null;
            if (igdbGame.getScreenshots().size() > 0) {
                screenshot = igdbGame.getScreenshots().get(0).getExtraLarge();
            }
            String cover = null;
            if (igdbGame.cover() != null) {
                //noinspection ConstantConditions
                cover = igdbGame.cover().getExtraLarge();
            }
            detailStateRelay.accept(
                    GameDetailState.builder()
                            .loading(false)
                            .mainScreenShot(screenshot)
                            .cover(cover)
                            .name(igdbGame.name())
                            .releaseDate(igdbGame.firstReleaseDate() != null ?
                                    igdbGame.firstReleaseDate().format(DATE_TIME_FORMATTER) :
                                    null
                            )
                            .summary(igdbGame.summary())
                            .screenshots(!igdbGame.getScreenshots().isEmpty())
                            .videos(!igdbGame.getVideos().isEmpty())
                            .build()
            );
        };
    }

    /**
     * Consumer to pass loading false to the streams relay
     * @return
     */
    Consumer<Object> streamsLoaded() {
        return __ -> streamsStateRelay.accept(
                StreamsState.builder()
                        .loading(false)
                        .build()
        );
    }

    /**
     * Consumer to pass the error to the details relay
     * @return
     */
    Consumer<Throwable> detailsError() {
        return throwable -> {
            Timber.e(throwable, "Error loading game details");
            detailStateRelay.accept(
                    GameDetailState.builder()
                            .loading(false)
                            .errorRes(R.string.api_error_igdb_game)
                            .build()
            );
        };
    }

    /**
     * Cin
     * @return
     */
    Consumer<Throwable> streamsError() {
        return throwable -> {
            Timber.e(throwable, "Error loading game streamers");
            streamsStateRelay.accept(
                    StreamsState.builder()
                            .loading(false)
                            .errorRes(R.string.api_error_streams)
                            .build()
            );
        };
    }
}
