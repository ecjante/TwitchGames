package com.enrico.twitchgames.details;

import android.content.Context;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.test.TestUtils;
import com.enrico.twitchgames.ui.ScreenNavigator;
import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by enrico.
 */
public class GameDetailsPresenterTest {

    static {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    private static final long GAME_ID = 33214;
    private static final String GAME_NAME = "Fortnite";

    @Mock ScreenNavigator screenNavigator;
    @Mock GameRepository gameRepository;
    @Mock GameDetailsViewModel viewModel;

    @Mock Consumer<IgdbGame> gameConsumer;
    @Mock Consumer<Object> streamsConsumer;
    @Mock Consumer<Throwable> detailErrorConsumer;
    @Mock Consumer<Throwable> streamsErrorConsumer;

    @Mock RecyclerDataSource dataSource;

    private List<IgdbGame> games = TestUtils.loadJson(
            "mock/igdb/games/fortnite.json",
            Types.newParameterizedType(List.class, IgdbGame.class)
    );
    private IgdbGame game = games.get(0);
    private List<TwitchStream> streams = TestUtils.loadJson("mock/twitch/streams/get_streams.json", TwitchStreamsResponse.class).streams();
    private GameDetailsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(viewModel.processIgdbGame()).thenReturn(gameConsumer);
        when(viewModel.streamsLoaded()).thenReturn(streamsConsumer);
        when(viewModel.detailsError()).thenReturn(detailErrorConsumer);
        when(viewModel.streamsError()).thenReturn(streamsErrorConsumer);

        when(gameRepository.getGameInfo(GAME_ID, GAME_NAME)).thenReturn(Single.just(game));
        when(gameRepository.getStreams(GAME_ID, GAME_NAME)).thenReturn(Single.just(streams));
    }

    @Test
    public void gameDetails() throws Exception {
        initPresenter();

        verify(gameConsumer).accept(game);
    }

    @Test
    public void gameDetailsError() throws Exception {
        Throwable t = new IOException();
        when(gameRepository.getGameInfo(GAME_ID, GAME_NAME)).thenReturn(Single.error(t));
        initPresenter();

        verify(detailErrorConsumer).accept(t);
    }

    @Test
    public void gameStreamers() throws Exception {
        initPresenter();

        verify(dataSource).setData(streams);
    }

    @Test
    public void gameStreamersError() throws Exception {
        Throwable t = new IOException();
        when(gameRepository.getStreams(GAME_ID, GAME_NAME)).thenReturn(Single.error(t));
        initPresenter();

        verify(streamsErrorConsumer).accept(t);
        // verify details still loaded
        verify(gameConsumer).accept(game);
    }

    @Test
    public void onStreamClicked() {
        TwitchStream twitchStream = TestUtils.loadJson("mock/twitch/streams/get_streams.json", TwitchStreamsResponse.class)
                .streams().get(0);
        initPresenter();
        presenter.onStreamClicked(twitchStream);

        verify(screenNavigator).openStream(twitchStream);
    }

    private void initPresenter() {
        presenter = new GameDetailsPresenter(GAME_ID, GAME_NAME, gameRepository, viewModel, screenNavigator, Mockito.mock(DisposableManager.class), dataSource);
    }
}