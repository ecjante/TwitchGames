package com.enrico.twitchgames.details;

import com.enrico.twitchgames.R;
import com.enrico.twitchgames.data.responses.TwitchStreamsResponse;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.twitch.TwitchStream;
import com.enrico.twitchgames.test.TestUtils;
import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by enrico.
 */
public class GameDetailsViewModelTest {

    private GameDetailsViewModel viewModel;

    private IgdbGame game = TestUtils.loadJson("mock/igdb/get_fortnite_game.json", IgdbGame.class);
    private List<TwitchStream> streams =
            TestUtils.loadJson("mock/twitch/get_streams.json", TwitchStreamsResponse.class)
            .streams();

    @Before
    public void setUp() throws Exception {
        viewModel = new GameDetailsViewModel();
    }

    @Test
    public void details() throws Exception {
        viewModel.processIgdbGame().accept(game);

        viewModel.details().test().assertValue(
                GameDetailState.builder()
                        .loading(false)
                        .mainScreenShot("https://images.igdb.com/igdb/image/upload/t_screenshot_big/wklmdcc9vn93257yjg1c.jpg")
                        .cover("https://images.igdb.com/igdb/image/upload/t_cover_big/j7lazlgtms7siqn7fn5y.jpg")
                        .name("Fortnite")
                        .summary("Fortnite is the living, action building game from the developer formerly known as Epic MegaGames. You and your friends will lead a group of Heroes to reclaim and rebuild a homeland that has been left empty by mysterious darkness only known as \"the Storm\". \n \nBand together online to build extravagant forts, find or build insane weapons and traps and protect your towns from the strange monsters that emerge during the Storm. In an action experience from the only company smart enough to attach chainsaws to guns, get out there to push back the Storm and save the world. And don't forget to loot all the things.")
                        .releaseDate("Jul 24, 2017")
                        .build()
        );
    }

    @Test
    public void detailsError() throws Exception {
        viewModel.detailsError().accept(new IOException());

        viewModel.details().test().assertValue(
                GameDetailState.builder()
                        .loading(false)
                        .errorRes(R.string.api_error_igdb_game)
                        .build()
        );
    }

    @Test
    public void streams() throws Exception {
        viewModel.processStreams().accept(streams);

        viewModel.streams().test().assertValue(
                StreamsState.builder()
                        .loading(false)
                        .streams(streams)
                        .build()
        );
    }

    @Test
    public void streamsError() throws Exception {
        viewModel.streamsError().accept(new IOException());

        viewModel.streams().test().assertValue(
                StreamsState.builder()
                        .loading(false)
                        .errorRes(R.string.api_error_streams)
                        .build()
        );
    }
}