package com.example.cbnu2.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends AppCompatActivity {

    private ActivityCalculatorBinding binding;
    private StringBuilder currentInput;
    private StringBuilder cumulativeInput;
    private String currentOperator = "";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentInput = new StringBuilder();
        cumulativeInput = new StringBuilder();
        binding.numInput.setText("0");
        binding.numResult.setText("0");

        setButtonClickListener(binding.btnZero, "0");
        setButtonClickListener(binding.btnOne, "1");
        setButtonClickListener(binding.btnTwo, "2");
        setButtonClickListener(binding.btnThree, "3");
        setButtonClickListener(binding.btnFour, "4");
        setButtonClickListener(binding.btnFive, "5");
        setButtonClickListener(binding.btnSix, "6");
        setButtonClickListener(binding.btnSeven, "7");
        setButtonClickListener(binding.btnEight, "8");
        setButtonClickListener(binding.btnNine, "9");
        setButtonClickListener(binding.btnDot, ".");
        setOperatorClickListener(binding.btnAdd, "+");
        setOperatorClickListener(binding.btnSub, "-");
        setOperatorClickListener(binding.btnMul, "*");
        setOperatorClickListener(binding.btnDiv, "/");
        setOperatorClickListener(binding.btnMod, "%");
        setEqualsClickListener(binding.btnEqual);
        setBackspaceClickListener(binding.btnBackspace);
        setClearClickListener(binding.btnCancle);


    }

    // 숫자, .버튼 클릭시 함수
    private void setButtonClickListener(Button button, final String value) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.matches("[0-9]") || value.equals(".")) {
                    handleNumericInput(value);
                }
            }
        });
    }

    // 연산자 클릭 함수
    private void setOperatorClickListener(Button button, final String operator) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperatorClick(operator);
            }
        });
    }

    private void setEqualsClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEqualsClick();
            }
        });
    }
    private void setBackspaceClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.toString().isEmpty()) {
                    return ;
                } else if (currentInput.toString().length() == 1) {
                    currentInput.deleteCharAt(currentInput.length() -1);
                    binding.numInput.setText("0");
                    binding.numResult.setText("0");
                    return ;
                }
                // 현재 입력된 문자열에서 마지막 한 글자 제거
                currentInput.deleteCharAt(currentInput.length() - 1);
                // 수정된 문자열을 numInput에 설정
                binding.numInput.setText(currentInput.toString());
                binding.numResult.setText(currentInput.toString());
            }
        });
    }
    private void setClearClickListener(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCurrentInput();
                clearCumulativeInput();
                currentOperator = "";
                binding.numInput.setText("0");
                binding.numResult.setText("0");
            }
        });
    }

    private void handleNumericInput(String value) {
        if (value.equals(".") && currentInput.toString().contains(".")) {
            makeToast("소수점은 한 번만 입력 가능합니다");
        } else {
            appendToCurrentInput(value);
        }

    }

    private void handleOperatorClick(String operator) {
        if (currentInput.toString().isEmpty()) {
            makeToast("숫자를 먼저 입력하세요");
            return;
        }
        if(currentOperator == "") {
            appendToCumulativeInput(currentInput.toString());
            clearCurrentInput();
            currentOperator = operator;
        } else {
            handleEqualsClick();
        }

    }

    private void handleEqualsClick() {
        if (currentInput.toString().isEmpty()) {
            makeToast("숫자를 먼저 입력하세요");
            return;
        }
        if(currentOperator == "") {
            return ;
        }
        double result = performCalculation(
                Double.parseDouble(cumulativeInput.toString()),
                Double.parseDouble(currentInput.toString()),
                currentOperator
        );
        if(result % 1.0 == 0) {
            int intResult = (int)result;
            binding.numResult.setText(String.valueOf(intResult));
            binding.numInput.setText(String.valueOf(intResult));
            clearCurrentInput();
            appendToCurrentInput(String.valueOf(intResult));
            clearCumulativeInput();
            currentOperator = "";
        } else {
            binding.numResult.setText(String.valueOf(result));
            binding.numInput.setText(String.valueOf(result));
            clearCurrentInput();
            appendToCurrentInput(String.valueOf(result));
            clearCumulativeInput();
            currentOperator = "";
        }


    }

    private double performCalculation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                // Handle division by zero
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    makeToast("0으로 나눌 수 없습니다");
                    return operand1;
                }
            case "%":
                // Handle division by zero
                if (operand2 != 0) {
                    return operand1 % operand2;
                } else {
                    makeToast("0으로 나머지 연산 할 수 없습니다");
                    return operand1; //
                }
            default:
                makeToast("잘못된 연산자입니다");
                return Double.NaN;
        }
    }


    private void appendToCurrentInput(String value) {
        currentInput.append(value);
        binding.numInput.setText(currentInput.toString());
        binding.numResult.setText(currentInput.toString());
    }

    private void appendToCumulativeInput(String value) {
        cumulativeInput.append(value);
        binding.numResult.setText(cumulativeInput.toString());
    }

    private void clearCurrentInput() {
        currentInput.setLength(0);
    }

    private void clearCumulativeInput() {
        cumulativeInput = new StringBuilder();
    }

    // 토스트 메시지 함수
    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}