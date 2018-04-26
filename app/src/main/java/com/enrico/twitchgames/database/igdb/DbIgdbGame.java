package com.enrico.twitchgames.database.igdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.enrico.twitchgames.models.igdb.IgdbEsrb;
import com.enrico.twitchgames.models.igdb.IgdbGame;
import com.enrico.twitchgames.models.igdb.IgdbGameCover;
import com.enrico.twitchgames.models.igdb.IgdbGameScreenshot;
import com.enrico.twitchgames.models.igdb.IgdbGameVideo;
import com.enrico.twitchgames.models.igdb.IgdbPegi;
import com.enrico.twitchgames.models.igdb.IgdbWebsite;

import org.threeten.bp.ZonedDateTime;

import java.util.List;

/**
 * Created by enrico.
 */
@Entity
public class DbIgdbGame {

    @PrimaryKey
    private final long id;

    @ColumnInfo(name = "igdb_id")
    private final long igdbId;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "summary")
    private final String summary;

    @ColumnInfo(name = "firstReleaseDate")
    private final ZonedDateTime firstReleaseDate;

    @ColumnInfo(name = "screenshots")
    private final List<IgdbGameScreenshot> screenshots;

    @ColumnInfo(name = "videos")
    private final List<IgdbGameVideo> videos;

    @ColumnInfo(name = "cover")
    private final IgdbGameCover cover;

    @ColumnInfo(name = "esrb")
    private final IgdbEsrb esrb;

    @ColumnInfo(name = "pegi")
    private final IgdbPegi pegi;

    @ColumnInfo(name = "websites")
    private final List<IgdbWebsite> websites;

    public DbIgdbGame(long id, long igdbId, String name, String summary, ZonedDateTime firstReleaseDate,
                      List<IgdbGameScreenshot> screenshots, List<IgdbGameVideo> videos,
                      IgdbGameCover cover, IgdbEsrb esrb, IgdbPegi pegi, List<IgdbWebsite> websites) {
        this.id = id;
        this.igdbId = igdbId;
        this.name = name;
        this.summary = summary;
        this.firstReleaseDate = firstReleaseDate;
        this.screenshots = screenshots;
        this.videos = videos;
        this.cover = cover;
        this.esrb = esrb;
        this.pegi = pegi;
        this.websites = websites;
    }

    public long getId() {
        return id;
    }

    public long getIgdbId() {
        return igdbId;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public ZonedDateTime getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public List<IgdbGameScreenshot> getScreenshots() {
        return screenshots;
    }

    public List<IgdbGameVideo> getVideos() {
        return videos;
    }

    public IgdbGameCover getCover() {
        return cover;
    }

    public IgdbEsrb getEsrb() {
        return esrb;
    }

    public IgdbPegi getPegi() {
        return pegi;
    }

    public List<IgdbWebsite> getWebsites() {
        return websites;
    }

    public static DbIgdbGame build(IgdbGame game) {
        return new DbIgdbGame(game.twitchId(), game.id(), game.name(), game.summary(), game.firstReleaseDate(),
                game.getScreenshots(), game.getVideos(), game.cover(), game.esrb(), game.pegi(),
                game.getWebsites());
    }
}
