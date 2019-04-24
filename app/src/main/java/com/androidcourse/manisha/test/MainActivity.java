package com.androidcourse.manisha.test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText nickName;
    private EditText age;
    private Button submit;
    TextView result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.editText_firstName);
        lastName = findViewById(R.id.editText_lastName);
        nickName = findViewById(R.id.editText_nickName);
        age = findViewById(R.id.editText_Age);
        submit = findViewById(R.id.button_show);
        result = findViewById(R.id.result);

        age.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean flag= false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    flag= true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(age.getApplicationWindowToken(), 0);
                }
                return flag;
            }
        });

        if(!age.toString().equals(null)){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        QuizDbAdapter quizDbAdapter = new QuizDbAdapter(getApplicationContext());
        Users user = quizDbAdapter.getUserData();
        if(user != null) {
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            nickName.setText(user.getNickName());
            age.setText(""+user.getAge());

            int finalScore =  quizDbAdapter.getScore(user.getId());
            result.setText("YOUR PREVIOUS SCORE :   " + finalScore);

        }

    }

    public void addUser(View view)
    {
        QuizDbAdapter dbHandler = new QuizDbAdapter(getApplicationContext());
        int id = 1;
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        String  nick_name = nickName.getText().toString();
        String age_text = age.getText().toString();
        int age_number = Integer.parseInt(age_text);
        Users user = new Users(id,first_name,last_name,nick_name,age_number);
        if(first_name.isEmpty() || last_name.isEmpty() || nick_name.isEmpty() || age_text.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please fill all the details!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            id = (int)dbHandler.addUser(user);
            Intent intent = new Intent(MainActivity.this,QuizActivity.class);
            intent.putExtra("userId", id);
            Toast.makeText(getApplicationContext(),"All The Best!",Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        QuizDbAdapter quizDbAdapter = new QuizDbAdapter(getApplicationContext());
        if (requestCode == 1) {
            int userId = data.getIntExtra("userId", 0);
            int score = data.getIntExtra("score", 0);
            Users user = quizDbAdapter.getUserData();

            saveScoreReport(userId, score);
            int finalScore =  quizDbAdapter.getScore(userId);
            String userName = quizDbAdapter.getUserName(userId);
            result.setText("NAME:"+user.getFirstName()+"\n"+"Your Score:"+finalScore+"\n"+ "USERNAME:"+ userName);
        }
    }
    private void saveScoreReport(int userId, int score) {
        QuizDbAdapter quizDbAdapter = new QuizDbAdapter(getApplicationContext());
        int scoreId = (int) quizDbAdapter.addScore(userId , score);
        Log.i("scoreid", scoreId+"");
    }
}
