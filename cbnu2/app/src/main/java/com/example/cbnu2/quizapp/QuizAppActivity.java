package com.example.cbnu2.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityQuizAppBinding;

public class QuizAppActivity extends AppCompatActivity {

    private ActivityQuizAppBinding binding;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString().trim();
                if(name.isEmpty()) {
                    makeToast("Please enter your name.");
                } else {
                    final Intent intent = new Intent(QuizAppActivity.this, QuizQuestionsActivity.class);
                    intent.putExtra(Constants.USER_NAME, binding.etName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void makeToast(String message) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}