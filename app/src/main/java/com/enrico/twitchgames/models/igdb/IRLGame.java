package com.enrico.twitchgames.models.igdb;

import org.threeten.bp.ZonedDateTime;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
 */
public class IRLGame extends IgdbGame {
    @Override
    public Long id() {
        return null;
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
    public List<Integer> platforms() {
        return Collections.emptyList();
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
