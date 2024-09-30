package com.dungmac.quanlymonhoc;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Subject implements Parcelable {
    private int id;
    private String Name;
    private int Count;
    private String ImagePath;
    private int GradeId;
    private Boolean Status;

    protected Subject(Parcel in) {
        id = in.readInt();
        Name = in.readString();
        Count = in.readInt();
        ImagePath = in.readString();
        GradeId = in.readInt();
        byte tmpStatus = in.readByte();
        Status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        this.Status = status;
    }

    public Subject() {
    }

    public Subject(String name, int count, String imagePath, int gradeId) {
        Name = name;
        Count = count;
        ImagePath = imagePath;
        GradeId = gradeId;
        Status = false;
    }

    public Subject(int id, String name, int count, String imagePath, int gradeId) {
        this.id = id;
        Name = name;
        Count = count;
        ImagePath = imagePath;
        GradeId = gradeId;
        Status = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setGradeId(int gradeId) {
        GradeId = gradeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public int getCount() {
        return Count;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public int getGradeId() {
        return GradeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Name);
        dest.writeInt(Count);
        dest.writeString(ImagePath);
        dest.writeInt(GradeId);
    }
}
