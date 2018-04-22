package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.TwitchRequester;
import com.enrico.twitchgames.data.TwitchTopGamesResponse;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.test.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by enrico.
 */
public class TopGamesPresenterTest {

    @Mock GameRepository gameRepository;
    @Mock TopGamesViewModel viewModel;
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<List<TwitchTopGame>> onSuccessConsumer;
    @Mock Consumer<Boolean> loadingConsumer;

    private TopGamesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(viewModel.loadingUpdated()).thenReturn(loadingConsumer);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.topGamesUpdated()).thenReturn(onSuccessConsumer);
    }

    @Test
    public void gamesLoaded() throws Exception {
        List<TwitchTopGame> games = setUpSuccess();
        initializePresenter();

        verify(gameRepository).getTopGames();
        verify(onSuccessConsumer).accept(games);
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void gamesLoadedError() throws Exception {
        Throwable error = setUpError();
        initializePresenter();

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
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
        // TODO
    }

    private List<TwitchTopGame> setUpSuccess() {
        TwitchTopGamesResponse response = TestUtils.loadJson("mock/twitch/get_top_games.json", TwitchTopGamesResponse.class);
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
        presenter = new TopGamesPresenter(viewModel, gameRepository);
    }
}