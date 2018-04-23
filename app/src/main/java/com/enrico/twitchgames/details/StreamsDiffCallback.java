package com.enrico.twitchgames.details;

import android.support.v7.util.DiffUtil;

import com.enrico.twitchgames.models.twitch.TwitchStream;

import java.util.List;

/**
 * Created by enrico.
 */
public class StreamsDiffCallback extends DiffUtil.Callback {

    private final List<TwitchStream> oldList;
    private final List<TwitchStream> newList;

    public StreamsDiffCallback(List<TwitchStream> oldList, List<TwitchStream> newList) {
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
        return oldList.get(oldItemPosition).id().equals(newList.get(newItemPosition).id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
