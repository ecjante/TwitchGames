package com.enrico.twitchgames.topgames;

import com.enrico.poweradapter.adapter.RecyclerDataSource;
import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;
import com.enrico.twitchgames.lifecycle.DisposableManager;
import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.test.TestUtils;
import com.enrico.twitchgames.ui.ScreenNavigator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by enrico.
 */
public class TopGamesPresenterTest {

    static {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Mock GameRepository gameRepository;
    @Mock TopGamesViewModel viewModel;
    @Mock ScreenNavigator screenNavigator;
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<Boolean> loadingConsumer;
    @Mock RecyclerDataSource dataSource;

    private TopGamesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(viewModel.loadingUpdated()).thenReturn(loadingConsumer);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.topGamesUpdated()).thenReturn(() -> {});
    }

    @Test
    public void gamesLoaded() throws Exception {
        List<TwitchTopGame> games = setUpSuccess();
        initializePresenter();

        verify(gameRepository).getTopGames();
        verify(dataSource).setData(games);
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void gamesLoadedError() throws Exception {
        Throwable error = setUpError();
        initializePresenter();

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(dataSource);
    }

    @Test
    public void loadingSuccess() throws Exception {
        setUpSuccess();
        initializePresenter();

        InOrder inOrder = Mockito.inOrder(loadingConsumer);
        inOrder.verify(loadingConsumer).accept(true);
        inOrder.verify(loadingConsumer).accept(false);
    }

    @Test
    public void loadingError() throws Exception {
        //noinspection ThrowableNotThrown
        setUpError();
        initializePresenter();

        InOrder inOrder = Mockito.inOrder(loadingConsumer);
        inOrder.verify(loadingConsumer).accept(true);
        inOrder.verify(loadingConsumer).accept(false);
    }

    @Test
    public void onTopGameClicked() {
        TwitchTopGame topGame = TestUtils.loadJson("mock/twitch/games/top/get_top_games.json", TwitchTopGamesResponse.class)
                .games().get(1);
        setUpSuccess();
        initializePresenter();
        presenter.onTopGameClicked(topGame.game());

        TwitchGame game = topGame.game();
        verify(screenNavigator).goToGameDetails(game.id(), game.name(), game.box().template());
    }

    private List<TwitchTopGame> setUpSuccess() {
        TwitchTopGamesResponse response = TestUtils.loadJson("mock/twitch/games/top/get_top_games.json", TwitchTopGamesResponse.class);
        List<TwitchTopGame> games = response.games();

        when(gameRepository.getTopGames()).thenReturn(Single.just(games));

        return games;
    }

    private Throwable setUpError() {
        Throwable error = new IOException();

        when(gameRepository.getTopGames()).thenReturn(Single.error(error));

        return error;
    }

    private void initializePresenter() {
        presenter = new TopGamesPresenter(viewModel, gameRepository, screenNavigator, Mockito.mock(DisposableManager.class), dataSource);
    }
}