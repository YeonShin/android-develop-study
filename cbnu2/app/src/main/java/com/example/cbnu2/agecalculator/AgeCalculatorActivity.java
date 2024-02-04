package com.example.cbnu2.agecalculator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.widget.Toast;

import com.example.cbnu2.databinding.ActivityAgeCalculatorBinding;


public class AgeCalculatorActivity extends AppCompatActivity {

    private ActivityAgeCalculatorBinding binding;
    private Toast toast;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgeCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                Calendar currentDate = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.KOREAN);
                String selectedFormatDate = sdf.format(selectedDate.getTime());


                long ageInMinutes = (currentDate.getTimeInMillis() - selectedDate.getTimeInMillis()) / 60000;
                long age = ageInMinutes / 525600;


                binding.textDate.setText(selectedFormatDate);
                binding.textMin.setText(String.valueOf(ageInMinutes));
                binding.textAge.setText(String.valueOf(age));
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        binding.btnDatePicker.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 makeToast("btnDatePicker Pressed");
                 datePickerDialog.show();
             }
         }
        );
    }

    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}