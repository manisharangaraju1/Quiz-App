package com.androidcourse.manisha.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizDbAdapter extends SQLiteOpenHelper {

    SQLiteDatabase db = null;

    public QuizDbAdapter(Context context) {

        super(context, QuizConstants.DATABASE_NAME, null, QuizConstants.DATABASE_Version);
        db = getWritableDatabase();
    }

    public long addUser(Users user)
    {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizConstants.FIRST_NAME,user.getFirstName());
        contentValues.put(QuizConstants.LAST_NAME,user.getLastName());
        contentValues.put(QuizConstants.NICK_NAME,user.getNickName());
        contentValues.put(QuizConstants.AGE,user.getAge());
        long id = db.insert(QuizConstants.USERS_TABLE_NAME, null , contentValues);
        return id;
    }

    public long addScore(int userId, int score)
    {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizConstants.USERID, userId);
        contentValues.put(QuizConstants.SCORE, score);
        long id = db.insert(QuizConstants.SCORE, null , contentValues);
        return id;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuizConstants.CREATE_USER);
        db.execSQL(QuizConstants.CREATE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(QuizConstants.USERS_DROP_TABLE);
            db.execSQL(QuizConstants.SCORE_DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Log.i("Exception",e.toString());
        }

    }
    public Users getUserData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {QuizConstants.ID,QuizConstants.FIRST_NAME,QuizConstants.LAST_NAME,QuizConstants.NICK_NAME,QuizConstants.AGE};
        Cursor cursor =db.query("user",columns,null,null,null,null,QuizConstants.ID+" DESC");

            while (cursor.moveToNext()){
                int cid =cursor.getInt(cursor.getColumnIndex(QuizConstants.ID));
                String firstName =cursor.getString(cursor.getColumnIndex(QuizConstants.FIRST_NAME));
                String lastName =cursor.getString(cursor.getColumnIndex(QuizConstants.LAST_NAME));
                String nickName =cursor.getString(cursor.getColumnIndex(QuizConstants.NICK_NAME));
                int age =cursor.getInt(cursor.getColumnIndex(QuizConstants.AGE));
                Users user = new Users(cid,firstName,lastName,nickName,age);
                return user;
            }

            cursor.close();
            return null;
    }
    public String getUserName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables("user");
        sqLiteQueryBuilder.appendWhere("id=" + userId);
        Cursor cursor = sqLiteQueryBuilder.query(db, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(1).concat(" ").concat(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return "";
    }
    public int getScore(int userId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {QuizConstants.ID,QuizConstants.USERID,QuizConstants.SCORE};
        Cursor cursor =db.query(QuizConstants.SCORE_TABLE_NAME,columns,QuizConstants.USERID+"="+ userId,null,null,null,null);

        while (cursor.moveToNext()){
            int score =cursor.getInt(cursor.getColumnIndex(QuizConstants.SCORE));

            return score;
        }

        cursor.close();
        return 0;
    }

}
