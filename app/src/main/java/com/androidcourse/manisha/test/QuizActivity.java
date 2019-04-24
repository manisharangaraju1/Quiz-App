package com.androidcourse.manisha.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements ResultInterface {

    private List<Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionList = getQuestions();

        createFragment(1);
    }

    private Bundle getQuestion(int questionId) {
        Bundle bundle = new Bundle();
        for (Question question: questionList) {
            if (questionId == question.getQid()) {
                bundle.putInt("questionId", question.getQid());
                bundle.putString("question", question.getQuestion());
                bundle.putString("option1", question.getOption1());
                bundle.putString("option2", question.getOption2());
                bundle.putString("option3", question.getOption3());
                bundle.putString("option4", question.getOption4());
                bundle.putInt("answer", question.getAnswerNumber());
            }
        }
        return bundle;
    }

    private void createFragment(int questionId) {
        QuizFragment quizFragment = new QuizFragment();
        FragmentManager fragmentManager= getSupportFragmentManager();
        quizFragment.setArguments(getQuestion(questionId));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.Container,quizFragment,"QuizFragment");
        transaction.addToBackStack("question");
        transaction.commit();
    }


    private String loadJsonFile() {
        String json ="";
        Resources res = getResources();
        InputStream inputStream = res.openRawResource(R.raw.questions_json);
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }
        return (builder.toString());
    }


    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJsonFile());
            JSONArray questionsFromJSON = obj.getJSONArray(QuizConstants.QUESTIONS);
            for (int i = 0; i < questionsFromJSON.length(); i++){
                JSONObject jsonObject = questionsFromJSON.getJSONObject(i);
                int id = jsonObject.getInt(QuizConstants.QID);
                String question = jsonObject.getString(QuizConstants.COLUMN_QUESTION);
                String option1 = jsonObject.getString(QuizConstants.COLUMN_OPTION1);
                String option2 = jsonObject.getString(QuizConstants.COLUMN_OPTION2);
                String option3 = jsonObject.getString(QuizConstants.COLUMN_OPTION3);
                String option4 = jsonObject.getString(QuizConstants.COLUMN_OPTION4);
                int answer = jsonObject.getInt(QuizConstants.COLUMN_ANSWER_NUMBER);
                boolean isCorrect = jsonObject.getBoolean(QuizConstants.IS_CORRECT);
                questions.add(new Question(id, question,option1,option2,option3,option4,answer,isCorrect));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public void crateNewQuestionFragment(int nextQuestion, int selectedAnswer) {
        updateScore(nextQuestion-1, selectedAnswer);
        if (nextQuestion > questionList.size()) {
            generateFinalReport();
        } else {
            createNewFragment(nextQuestion);
        }
    }
    private void createNewFragment(int nextQuestion) {
        QuizFragment quizFragment = new QuizFragment();
        FragmentManager fragmentManager= getSupportFragmentManager();
        quizFragment.setArguments(getQuestion(nextQuestion));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Container,quizFragment,"QuizFragment");
        transaction.addToBackStack("question");
        transaction.commit();
    }

    private void generateFinalReport() {
        int score = 0;
        for (Question question: questionList) {
            if (question.isCorrect() == true) {
                score++;
            }
        }
        Intent toPassBack = getIntent();
        toPassBack.putExtra("score", score);
        toPassBack.putExtra("userId", getIntent().getExtras().getInt("userId"));
        setResult(RESULT_OK, toPassBack);
        finish();
    }

    private void updateScore(int currentQuestionId, int selectedAnswer) {
        for (Question question: questionList) {
            if (question.getQid() == currentQuestionId && question.getAnswerNumber() == selectedAnswer) {
                question.setCorrect(true);
            }
        }
    }
}
