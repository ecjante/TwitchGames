package com.enrico.twitchgames.models.igdb.twitchonlygames;

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
public class IRLGame extends IgdbGame {
    @Override
    public Long id() {
        return 494717L;
    }

    @Override
    public String name() {
        return "IRL";
    }

    @Override
    public String summary() {
        return "Share your Thoughts, Opinions and Everyday Life";
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
            public String big() {
                return "https://static-cdn.jtvnw.net/ttv-boxart/IRL-272x380.jpg";
            }

            @Override
            public String small() {
                return big();
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
}
