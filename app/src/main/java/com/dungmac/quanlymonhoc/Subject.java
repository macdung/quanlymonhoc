package com.dungmac.quanlymonhoc;

public class Subject {
    private int id;
    private String Name;
    private int Count;
    private String ImagePath;
    private int GradeId;

    public Subject() {
    }

    public Subject(int id, String name, int count, String imagePath, int gradeId) {
        this.id = id;
        Name = name;
        Count = count;
        ImagePath = imagePath;
        GradeId = gradeId;
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
}
