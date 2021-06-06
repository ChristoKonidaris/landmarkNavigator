package com.example.landmarknavigator.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.landmarknavigator.Location;

public class Item implements Parcelable {
    public String title;
    public String street;
    public String post;
    public double lat;
    public double lon;
    public String phone;
    public String website;

    //empty constructor
    public Item(){}

    public Item(String title, String street, String post, double lat, double lon, String phone, String website) {
        this.title = title;
        this.street = street;
        this.post = post;
        this.lat = lat;
        this.lon = lon;
        this.phone = phone;
        this.website = website;
    }

    protected Item(Parcel in) {
        title = in.readString();
        street = in.readString();
        post = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        phone = in.readString();
        website = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(street);
        dest.writeString(post);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(phone);
        dest.writeString(website);
    }

    public String getTitle() {
        return title;
    }

    public String getStreet() {
        return street;
    }

    public String getPost() {
        return post;
    }
}
