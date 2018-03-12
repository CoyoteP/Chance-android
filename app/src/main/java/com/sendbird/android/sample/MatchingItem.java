package com.sendbird.android.sample;

import java.util.ArrayList;

/**
 * Created by Coyote on 2018/01/23.
 */

public class MatchingItem {

    private int id;
    private String userid;
    private String username;
    private ArrayList<String> tags;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
