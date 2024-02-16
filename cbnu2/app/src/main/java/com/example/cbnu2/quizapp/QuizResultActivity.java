package com.example.cbnu2.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityQuizResultBinding;

public class QuizResultActivity extends AppCompatActivity {
    private ActivityQuizResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvName.setText(getIntent().getStringExtra(Constants.USER_NAME));
        int totalQuestions = getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 0);
        int correctAnswers = getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0);
        binding.tvScore.setText("Your Score is " + correctAnswers + " out of " + totalQuestions);

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResultActivity.this, QuizAppActivity.class));
            }
        });

    }
}