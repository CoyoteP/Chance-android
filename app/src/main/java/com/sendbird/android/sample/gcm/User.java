package com.sendbird.android.sample.gcm;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Coyote on 2018/01/26.
 */

public class User {
    private String userid;
    private String username;
    private String gender;
    private String age;
    //private Bitmap photo;
    private String profile;
    private ArrayList<String> tag;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }


}
