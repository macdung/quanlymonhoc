package com.dungmac.quanlymonhoc;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    //tên bảng
    public static final String Table1Name = "GradeTable";
    //tên các cột trong bảng
    public static final String GradeId = "Id";
    public static final String GradeName = "Name";

    //tên bảng
    public static final String Table2Name = "SubjectTable";
    //tên các cột trong bảng
    public static final String SubjectId = "Id";
    public static final String SubjectName = "Name";
    public static final String SubjectCount = "Count";
    public static final String SubjectImagePath = "ImagePath";

    public MyDB(Context context, String name,
                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + Table1Name + "("
                + GradeId + " Integer Primary key AUTOINCREMENT, "
                + GradeName + " Text) ";
        //chạy câu truy vấn SQL để tạo bảng
        db.execSQL(sqlCreate);

        //tạo câu sql để tạo bảng TableContact
        String sqlCreate2 = "Create table if not exists " + Table2Name + "("
                + SubjectId + " Integer Primary key AUTOINCREMENT, "
                + SubjectName + " Text, "
                + SubjectCount + " Integer, "
                + SubjectImagePath + " Text, "
                + GradeId + " INTEGER, "
                + "FOREIGN KEY (" + GradeId + ") REFERENCES " + Table1Name + "(" + GradeId + ") ON DELETE CASCADE)";
        //chạy câu truy vấn SQL để tạo bảng
        db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xóa bảng nếu đã có
        db.execSQL("Drop table if exists " + Table1Name);
        db.execSQL("Drop table if exists " + Table2Name);

        //tạo lại
        onCreate(db);
    }

    //hàm thêm một contact vào bảng TableContact
    public void addGrade(Grade grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(GradeId, grade.getId());
        value.put(GradeName, grade.getName());
        db.insert(Table1Name, null, value);
        db.close();
    }

    public void updateGrade(int id, Grade grade) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(GradeId, grade.getId());
        value.put(GradeName, grade.getName());
        db.update(Table1Name, value, GradeId + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteGrade(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table1Name, GradeId + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<Grade> getAllGrade() {
        ArrayList<Grade> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + Table1Name;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql, null);
        //tạo ArrayList<Grade> để trả về;
        if (cursor != null)
            while (cursor.moveToNext()) {
                Grade user = new Grade(cursor.getInt(0),
                        cursor.getString(1));
                list.add(user);
            }
        return list;
    }

    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(SubjectName, subject.getName());
        value.put(SubjectCount, subject.getCount());
        value.put(SubjectImagePath, subject.getImagePath());
        value.put(GradeId, subject.getGradeId());  // Add GradeId
        db.insert(Table2Name, null, value);
        db.close();
    }

    public void updateSubject(int id, Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(SubjectName, subject.getName());
        value.put(SubjectCount, subject.getCount());
        value.put(SubjectImagePath, subject.getImagePath());
        value.put(GradeId, subject.getGradeId());  // Ensure GradeId is linked
        db.update(Table2Name, value, SubjectId + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteSubject(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table2Name, SubjectId + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Subject> getAllSubjectsForGrade(int gradeId) {
        ArrayList<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Table2Name + " WHERE " + GradeId + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(gradeId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Subject subject = new Subject(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4));  // GradeId
                list.add(subject);
            }
        }
        return list;
    }


}


