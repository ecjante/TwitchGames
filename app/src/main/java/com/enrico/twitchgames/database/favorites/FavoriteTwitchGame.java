package com.enrico.twitchgames.database.favorites;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.enrico.twitchgames.models.twitch.TwitchGame;
import com.enrico.twitchgames.models.twitch.TwitchTopGame;

/**
 * Created by enrico.
 */
@Entity
public class FavoriteTwitchGame {

    @PrimaryKey
    private final long id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "box_template")
    private final String boxTemplate;

    FavoriteTwitchGame(long id, String name, String boxTemplate) {
        this.id = id;
        this.name = name;
        this.boxTemplate = boxTemplate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBoxTemplate() {
        return boxTemplate;
    }

    public static FavoriteTwitchGame build(TwitchGame game) {
        return new FavoriteTwitchGame(game.id(), game.name(), game.box().template());
    }
}
