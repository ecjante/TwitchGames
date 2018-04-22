package com.enrico.twitchgames.models.igdb;

import org.threeten.bp.ZonedDateTime;

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
        return null;
    }

    @Override
    public List<IgdbGameScreenshot> screenshots() {
        return null;
    }

    @Override
    public List<IgdbGameVideo> videos() {
        return null;
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
                return null;
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
        return null;
    }
}
