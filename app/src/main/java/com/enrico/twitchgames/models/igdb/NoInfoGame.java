package com.enrico.twitchgames.models.igdb;

import org.threeten.bp.ZonedDateTime;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
 */
public class NoInfoGame extends IgdbGame {
    @Override
    public Long id() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String summary() {
        return null;
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
        return null;
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
