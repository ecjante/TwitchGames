package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.data.TwitchTopGamesResponse;
import com.enrico.twitchgames.test.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import io.reactivex.observers.TestObserver;

/**
 * Created by enrico.
 */
public class TopGamesViewModelTest {

    private TopGamesViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new TopGamesViewModel();
    }

    @Test
    public void loading() throws Exception {
        TestObserver<Boolean> loadingObserver = viewModel.loading().test();
        viewModel.loadingUpdated().accept(true);
        viewModel.loadingUpdated().accept(false);

        loadingObserver.assertValues(true, false);
    }

    @Test
    public void topGames() throws Exception {
        TwitchTopGamesResponse response = TestUtils.loadJson("mock/twitch/get_top_games.json", TwitchTopGamesResponse.class);

        viewModel.topGamesUpdated().accept(response.games());

        viewModel.topGames().test().assertValue(response.games());
    }

    @Test
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();

        viewModel.onError().accept(new IOException());
        viewModel.topGamesUpdated().accept(Collections.emptyList());

        errorObserver.assertValues(R.string.api_error_top_games, -1);
    }
}