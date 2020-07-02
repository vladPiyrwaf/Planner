package com.example.simbirplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventInformation extends RealmObject implements Parcelable {
    @PrimaryKey
    private String id;
    private long dateStart;
    private long dateFinish;
    private long position;
    private String name;
    private String description;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(long dateFinish) {
        this.dateFinish = dateFinish;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.dateStart);
        dest.writeLong(this.dateFinish);
        dest.writeLong(this.position);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
    }

    public EventInformation() {
    }

    protected EventInformation(Parcel in) {
        this.id = in.readString();
        this.dateStart = in.readLong();
        this.dateFinish = in.readLong();
        this.position = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<EventInformation> CREATOR = new Creator<EventInformation>() {
        @Override
        public EventInformation createFromParcel(Parcel source) {
            return new EventInformation(source);
        }

        @Override
        public EventInformation[] newArray(int size) {
            return new EventInformation[size];
        }
    };
}
