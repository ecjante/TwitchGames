package com.enrico.twitchgames.topgames;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;
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
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();

        viewModel.onError().accept(new IOException());
        viewModel.topGamesUpdated().run();

        errorObserver.assertValues(R.string.api_error_top_games, -1);
    }
}