package com.enrico.twitchgames.data;

import com.enrico.twitchgames.data.responses.TwitchTopGamesResponse;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;
import com.enrico.twitchgames.test.TestUtils;
import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by enrico.
 */
public class GameRepositoryTest {

    @Mock Provider<TwitchRequester> twitchRequesterProvider;
    @Mock TwitchRequester twitchRequester;
    @Mock Provider<IgdbRequester> igdbRequesterProvider;
    @Mock IgdbRequester igdbRequester;

    private GameRepository repository;
    private TwitchTopGamesResponse twitchTopGamesResponse;
    private IgdbGame godOfWarGame;
    private IgdbGame fortniteGame;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(twitchRequesterProvider.get()).thenReturn(twitchRequester);
        when(igdbRequesterProvider.get()).thenReturn(igdbRequester);

        twitchTopGamesResponse = TestUtils.loadJson("mock/twitch/games/top/get_top_games.json", TwitchTopGamesResponse.class);
        when(twitchRequester.getTopGames()).thenReturn(Single.just(twitchTopGamesResponse.games()));

        List<IgdbGame> godOfWarResponse = TestUtils.loadJson(
                "mock/igdb/games/god_of_war.json",
                Types.newParameterizedType(List.class, IgdbGame.class)
        );
        godOfWarGame = godOfWarResponse.get(0);
        List<IgdbGame> fortniteResponse = TestUtils.loadJson(
                "mock/igdb/games/fortnite.json",
                Types.newParameterizedType(List.class, IgdbGame.class)
        );
        fortniteGame = fortniteResponse.get(0);

        repository = new GameRepository(twitchRequesterProvider, igdbRequesterProvider, Schedulers.trampoline());
    }

    @Test
    public void getTopGames() {
        repository.getTopGames().test().assertValue(twitchTopGamesResponse.games());

        List<TwitchTopGame> modifiedList = new ArrayList<>(twitchTopGamesResponse.games());
        modifiedList.remove(0);
        when(twitchRequester.getTopGames()).thenReturn(Single.just(modifiedList));

        repository.getTopGames().test().assertValue(twitchTopGamesResponse.games());
    }

    @Test
    public void getGameInfo() {
        repository.getTopGames().subscribe();

        TwitchTopGame twitchGodOfWarGame = twitchTopGamesResponse.games().get(0);

        // Return God of war to add to cache
        when(igdbRequester.getGameInfo(twitchGodOfWarGame.game().name())).thenReturn(Single.just(godOfWarGame));

        // Just assert God of War was returned
        repository.getGameInfo(twitchGodOfWarGame.game().id(), twitchGodOfWarGame.game().name())
                .test().assertValue(godOfWarGame);

        // Change the game to fortnite when getting game info
        when(igdbRequester.getGameInfo(anyString())).thenReturn(Single.just(fortniteGame));

        // Make sure the God of war is grabbed from cache
        repository.getGameInfo(twitchGodOfWarGame.game().id(), twitchGodOfWarGame.game().name())
                .test().assertValue(godOfWarGame);

        // Make sure Fortnite is grabbed from api instead of cache
        repository.getGameInfo(1, "Any game")
                .test().assertValue(fortniteGame);

    }
}