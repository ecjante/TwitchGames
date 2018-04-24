package com.enrico.twitchgames.details;

import android.content.Context;

import com.enrico.twitchgames.data.GameRepository;
import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.test.TestUtils;
import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by enrico.
 */
public class GameDetailsPresenterTest {

    private static final long GAME_ID = 33214;
    private static final String GAME_NAME = "Fortnite";

    @Mock GameRepository gameRepository;
    @Mock GameDetailsViewModel viewModel;
    @Mock Context context;

    @Mock Consumer<IgdbGame> gameConsumer;
    @Mock Consumer<List<TwitchStream>> streamsConsumer;
    @Mock Consumer<Throwable> detailErrorConsumer;
    @Mock Consumer<Throwable> streamsErrorConsumer;

    private List<IgdbGame> games = TestUtils.loadJson(
            "mock/igdb/games/fortnite.json",
            Types.newParameterizedType(List.class, IgdbGame.class)
    );
    private IgdbGame game = games.get(0);
    private List<TwitchStream> streams = TestUtils.loadJson("mock/twitch/streams/get_streams.json", TwitchStreamsResponse.class).streams();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(viewModel.processIgdbGame()).thenReturn(gameConsumer);
        when(viewModel.processStreams()).thenReturn(streamsConsumer);
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

        verify(streamsConsumer).accept(streams);
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

    private void initPresenter() {
        new GameDetailsPresenter(context, GAME_ID, GAME_NAME, gameRepository, viewModel);
    }
}