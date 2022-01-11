package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credentials {

    private Integer credentialsID;
    private String url;
    private String username;
    private String password;
    private String key;
    private Integer userId;

    public Credentials(Integer credentialsID, String url, String username, String password, String key, Integer userId) {
        this.credentialsID = credentialsID;
        this.url = url;
        this.username = username;
        this.password = password;
        this.key = key;
        this.userId = userId;
    }

    public Integer getCredentialsID() {
        return credentialsID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCredentialsID(Integer credentialsID) {
        this.credentialsID = credentialsID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
