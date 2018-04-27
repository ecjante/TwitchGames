package com.enrico.twitchgames.models.igdb;

import org.threeten.bp.ZonedDateTime;

import java.util.Collections;
import java.util.List;

/**
 * Created by enrico.
 *
 * Model for when there is no info for the game
 */
public final class NoInfoGame extends IgdbGame {

    private final long id;

    public NoInfoGame(long id) {
        this.id = id;
    }

    @Override
    public Long id() {
        return null;
    }

    @Override
    public Long twitchId() {
        return id;
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

    @Override
    protected Builder toBuilder() {
        return null;
    }
}
