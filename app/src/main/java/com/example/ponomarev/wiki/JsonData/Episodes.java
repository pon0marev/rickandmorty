package com.example.ponomarev.wiki.JsonData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Episodes implements Serializable {

    private List<Episode> episodes = null;

    public Episodes() {
        episodes=new ArrayList<>();
    }



    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode) {
        this.episodes.add(episode);
    }
}
