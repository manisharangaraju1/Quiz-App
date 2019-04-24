package com.androidcourse.manisha.test;

public class QuizConstants {

    public static final String QID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_ANSWER_NUMBER = "answer_number";


    public static final String QUESTIONS = "questions";
    public static final String USERS_TABLE_NAME = "user";
    public static final String SCORE_TABLE_NAME = "score";
    public static final String IS_CORRECT = "isCorrect";

    public static final String ID="id";
    public static final String FIRST_NAME="firstname";
    public static final String LAST_NAME="lastname";
    public static final String NICK_NAME="nickname";
    public static final String AGE="age";

    public static final String USERNAME="username";
    public static final String USERID="userid";
    public static final String SCORE="score";



    public static final String DATABASE_NAME = "Quiz";    // Database Name
    public static final int DATABASE_Version = 1;    // Database Version


    public static final String CREATE_USER = "create table user(id integer primary key AUTOINCREMENT, firstname text, lastname text, nickname text, age integer);";


    public static final String CREATE_SCORE = "create table score(id integer primary key AUTOINCREMENT, userid integer, score integer);";

    public static final String USERS_DROP_TABLE ="DROP TABLE IF EXISTS " + USERS_TABLE_NAME;

    public static final String SCORE_DROP_TABLE ="DROP TABLE IF EXISTS " + SCORE_TABLE_NAME;



}
