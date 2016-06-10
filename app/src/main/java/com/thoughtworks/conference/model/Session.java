package com.thoughtworks.conference.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import java.util.Date;

public class Session implements Parcelable {
  private String name;
  private String description;
  @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
  private Date startTime;
  @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
  private Date endTime;
  private Category category;
  private String location;

  public Session() {
  }

  public Session(String name, String description, Date startTime, Date endTime, Category category, String location) {
    this.name = name;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
    this.category = category;
    this.location = location;
  }

  public Date getStartTime() {
    return startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public String getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public Category getCategory() {
    return category;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.description);
    dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
    dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
    dest.writeInt(this.category == null ? -1 : this.category.ordinal());
    dest.writeString(this.location);
  }

  protected Session(Parcel in) {
    this.name = in.readString();
    this.description = in.readString();
    long tmpStartTime = in.readLong();
    this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
    long tmpEndTime = in.readLong();
    this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
    int tmpCategory = in.readInt();
    this.category = tmpCategory == -1 ? null : Category.values()[tmpCategory];
    this.location = in.readString();
  }

  public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
    @Override
    public Session createFromParcel(Parcel source) {
      return new Session(source);
    }

    @Override
    public Session[] newArray(int size) {
      return new Session[size];
    }
  };
}