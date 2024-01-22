package com.example.cbnu2.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends AppCompatActivity {

    private ActivityCalculatorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}