package com.enrico.twitchgames.models.igdb.twitchonlygames;

import android.support.annotation.Nullable;

import com.enrico.twitchgames.models.igdb.IgdbEsrb;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.igdb.IgdbGameCover;
import com.enrico.twitchgames.models.igdb.IgdbGameScreenshot;
import com.enrico.twitchgames.models.igdb.IgdbGameVideo;
import com.enrico.twitchgames.models.igdb.IgdbPegi;
import com.enrico.twitchgames.models.igdb.IgdbWebsite;

import org.threeten.bp.ZonedDateTime;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
 */
public class CreativeGame extends IgdbGame {
    @Override
    public Long id() {
        return 488191L;
    }

    @Nullable
    @Override
    public Long twitchId() {
        return 488191L;
    }

    @Override
    public String name() {
        return "Creative";
    }

    @Override
    public String summary() {
        return "Broadcasters can share the process of making their creations on Twitch Creative.";
    }

    @Override
    public ZonedDateTime firstReleaseDate() {
        return null;
    }

    @Override
    public List<IgdbGameScreenshot> screenshots() {
        return Collections.emptyList();
    }

    @Override
    public List<IgdbGameVideo> videos() {
        return Collections.emptyList();
    }

    @Override
    public IgdbGameCover cover() {
        return new IgdbGameCover() {
            @Override
            public String cloudinaryId() {
                return null;
            }

            @Override
            public String getLarge() {
                return "https://static-cdn.jtvnw.net/ttv-boxart/Creative-272x380.jpg";
            }

            @Override
            public String getSmall() {
                return getLarge();
            }
        };
    }

    @Override
    public IgdbEsrb esrb() {
        return null;
    }

    @Override
    public IgdbPegi pegi() {
        return null;
    }

    @Override
    public List<IgdbWebsite> websites() {
        return Collections.emptyList();
    }

    @Override
    protected Builder toBuilder() {
        return null;
    }
}
