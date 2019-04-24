package com.androidcourse.manisha.test;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener {
    Button next;
    TextView question;
    RadioGroup rbGroup;
    RadioButton option1_btn;
    RadioButton option2_btn;
    RadioButton option3_btn;
    RadioButton option4_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);

        next = view.findViewById(R.id.button_confirm_next);
        question = view.findViewById(R.id.text_view_question);
        rbGroup = view.findViewById(R.id.radio_group);
        option1_btn = view.findViewById(R.id.radio_button1);
        option2_btn = view.findViewById(R.id.radio_button2);
        option3_btn = view.findViewById(R.id.radio_button3);
        option4_btn = view.findViewById(R.id.radio_button4);

        showQuestionDetails();

        next.setOnClickListener(this);

        return view;
    }

    private void showQuestionDetails() {
        Bundle bundle = getArguments();
        question.setText(bundle.get("question").toString());
        option1_btn.setText(bundle.get("option1").toString());
        option2_btn.setText(bundle.get("option2").toString());
        option3_btn.setText(bundle.get("option3").toString());
        option4_btn.setText(bundle.get("option4").toString());

    }

    @Override
    public void onClick(View v) {
        int selectedAnswer = rbGroup.indexOfChild(getActivity().findViewById(rbGroup.getCheckedRadioButtonId())) + 1;
        int nextQuestion = getArguments().getInt("questionId") + 1;
        ResultInterface resultInterface = (ResultInterface) getActivity();
        resultInterface.crateNewQuestionFragment(nextQuestion, selectedAnswer);
    }
}









