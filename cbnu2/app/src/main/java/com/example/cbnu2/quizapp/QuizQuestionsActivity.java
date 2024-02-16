package com.example.cbnu2.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityQuizQuestionsBinding;

import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;

public class QuizQuestionsActivity extends AppCompatActivity implements View.OnClickListener {
    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionsList = null;
    private String mUserName;
    private int mSelectedOptionPosition = 0;
    private int mCorrectAnswers = 0;
    private ActivityQuizQuestionsBinding binding;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUserName = getIntent().getStringExtra(Constants.USER_NAME);
        mQuestionsList = Constants.getQuestions();

        setQuestion();

        binding.tvOptionOne.setOnClickListener(this);
        binding.tvOptionTwo.setOnClickListener(this);
        binding.tvOptionThree.setOnClickListener(this);
        binding.tvOptionFour.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if(viewId == R.id.tv_option_one) {
                if(binding.tvOptionOne != null) {
                    selectedOptionsView(binding.tvOptionOne, 1);
                }
        }
        else if(viewId == R.id.tv_option_two) {
            if(binding.tvOptionTwo != null) {
                selectedOptionsView(binding.tvOptionTwo, 2);
            }
        }
        else if(viewId == R.id.tv_option_three) {
            if(binding.tvOptionThree != null) {
                selectedOptionsView(binding.tvOptionThree, 3);
            }
        }
        else if(viewId == R.id.tv_option_four) {
            if(binding.tvOptionFour != null) {
                selectedOptionsView(binding.tvOptionFour, 4);
            }
        }
        else if(viewId == R.id.btn_submit) {
            if(mSelectedOptionPosition == 0) {
                mCurrentPosition++;

                if(mCurrentPosition <= mQuestionsList.size()) {
                    setQuestion();
                } else {
                    final Intent intent = new Intent(QuizQuestionsActivity.this, QuizResultActivity.class);
                    intent.putExtra(Constants.USER_NAME, mUserName);
                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers);
                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size());
                    startActivity(intent);
                }
            } else {
                Question question = mQuestionsList.get(mCurrentPosition - 1);
                if(question.getCorrectAnswer() != mSelectedOptionPosition) {
                    answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg);
                } else {
                    mCorrectAnswers++;
                }
                answerView(question.getCorrectAnswer(), R.drawable.correct_option_border_bg);
                if(mCurrentPosition == mQuestionsList.size()) {
                    binding.btnSubmit.setText("FINISH");
                } else {
                    binding.btnSubmit.setText("SUBMIT");
                }
                mSelectedOptionPosition = 0;
            }
        }
//        switch (viewId) {
//            case R.id.tv_option_one :
//                if(binding.tvOptionOne != null) {
//                    selectedOptionsView(binding.tvOptionOne, 1);
//                }
//                break;
//            case R.id.tv_option_two :
//                if(binding.tvOptionTwo != null) {
//                    selectedOptionsView(binding.tvOptionTwo, 2);
//                }
//                break;
//            case R.id.tv_option_three :
//                if(binding.tvOptionThree != null) {
//                    selectedOptionsView(binding.tvOptionThree, 3);
//                }
//                break;
//            case R.id.tv_option_four :
//                if(binding.tvOptionFour != null) {
//                    selectedOptionsView(binding.tvOptionFour, 4);
//                }
//                break;
//        }
    }

    private void setQuestion() {
        defaultOptionsView();
        Question question = null;
        if (mCurrentPosition - 1 >= 0 && mCurrentPosition - 1 < mQuestionsList.size()) {
            question = mQuestionsList.get(mCurrentPosition - 1);
        }
        if (binding.progressBar != null) {
            binding.progressBar.setProgress(mCurrentPosition);
        }

        String progressText = mCurrentPosition + "/" + binding.progressBar.getMax();

        binding.ivFlag.setImageResource(question.getImage());
        binding.tvProgress.setText(progressText);
        binding.tvQuestion.setText(question.getQuestion());
        binding.tvOptionOne.setText(question.getOptionOne());
        binding.tvOptionTwo.setText(question.getOptionTwo());
        binding.tvOptionThree.setText(question.getOptionThree());
        binding.tvOptionFour.setText(question.getOptionFour());

        if (mCurrentPosition == mQuestionsList.size()) {
            binding.btnSubmit.setText("FINISH");
        } else {
            binding.btnSubmit.setText("SUBMIT");
        }
    }

    private void defaultOptionsView() {
        ArrayList<TextView> options = new ArrayList<TextView>();
        if (binding.tvOptionOne != null) {
            options.add(0, binding.tvOptionOne);
        }
        if (binding.tvOptionTwo != null) {
            options.add(1, binding.tvOptionTwo);
        }
        if (binding.tvOptionThree != null) {
            options.add(2, binding.tvOptionThree);
        }
        if (binding.tvOptionFour != null) {
            options.add(3, binding.tvOptionFour);
        }
        for(TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.default_option_border_bg));
        }

    }
    private void selectedOptionsView(TextView tv, int selectedOptionNum) {
        defaultOptionsView();

        mSelectedOptionPosition = selectedOptionNum;

        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg));
    }

    private void answerView(int answer, int drawableView) {
        switch(answer) {
            case 1 : {
                if(binding.tvOptionOne != null) {
                    binding.tvOptionOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            }
            case 2 : {
                if(binding.tvOptionTwo != null) {
                    binding.tvOptionTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            }
            case 3 : {
                if(binding.tvOptionThree != null) {
                    binding.tvOptionThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            }
            case 4 : {
                if(binding.tvOptionFour != null) {
                    binding.tvOptionFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            }

        }
    }
    private void makeToast(String message) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}