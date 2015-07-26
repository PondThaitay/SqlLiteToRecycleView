package com.wisdomlanna.sqlliterecycleview;

/**
 * Created by suraphol on 7/26/15 AD.
 */
public class FavoritePlacesModel {

    private int id;
    private String userId;
    private String name;
    private String address;
    private String lat;
    private String lng;

    public FavoritePlacesModel() {
    }

    public FavoritePlacesModel(String userId, String name, String address, String lat, String lng) {
        super();
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return id + "/" + userId + "/" + name + "/" + address + "/" + lat + "/" + lng;
    }
}
