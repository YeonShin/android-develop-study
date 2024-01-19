package com.example.cbnu2.countperson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityCountPersonBinding;

public class CountPerson extends AppCompatActivity {
    private ActivityCountPersonBinding binding;
    private int count = 0;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textCount.setText(count + "");
        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                binding.textCount.setText(count + "");
                makeToast("증가했습니다!");

            }
        });
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                binding.textCount.setText(count + "");
                makeToast("감소했습니다!");
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

// https://latte-is-horse.tistory.com/284 참고 사항 (토스트 하나만 띄우기)




