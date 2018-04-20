package com.enrico.twitchgames.topgames;

import android.support.v7.util.DiffUtil;

import com.enrico.twitchgames.models.twitch.TwitchTopGame;

import java.util.List;

/**
 * Created by enrico on 3/6/18.
 */

public class TopGamesDiffCallback extends DiffUtil.Callback {

    private final List<TwitchTopGame> oldList;
    private final List<TwitchTopGame> newList;

    public TopGamesDiffCallback(List<TwitchTopGame> oldList, List<TwitchTopGame> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).game().id() == newList.get(newItemPosition).game().id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
