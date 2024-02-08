package com.example.cbnu2.calculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbnu2.R;
import com.example.cbnu2.databinding.ActivityCalculatorBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity {

    private TextView txtExpression;
    private TextView txtResult;
    private List<Integer> checkList;
    private Stack<String> operatorStack;
    private List<String> infixList;
    private List<String> postfixList;


    private ActivityCalculatorBinding binding;
    //    private StringBuilder currentInput;
//    private StringBuilder cumulativeInput;
//    private String currentOperator = "";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        currentInput = new StringBuilder();
//        cumulativeInput = new StringBuilder();
//        binding.numInput.setText("0");
//        binding.numResult.setText("0");

//        setButtonClickListener(binding.btnZero, "0");
//        setButtonClickListener(binding.btnOne, "1");
//        setButtonClickListener(binding.btnTwo, "2");
//        setButtonClickListener(binding.btnThree, "3");
//        setButtonClickListener(binding.btnFour, "4");
//        setButtonClickListener(binding.btnFive, "5");
//        setButtonClickListener(binding.btnSix, "6");
//        setButtonClickListener(binding.btnSeven, "7");
//        setButtonClickListener(binding.btnEight, "8");
//        setButtonClickListener(binding.btnNine, "9");
//        setButtonClickListener(binding.btnDot, ".");
//        setOperatorClickListener(binding.btnAdd, "+");
//        setOperatorClickListener(binding.btnSub, "-");
//        setOperatorClickListener(binding.btnMul, "*");
//        setOperatorClickListener(binding.btnDiv, "/");
//        setOperatorClickListener(binding.btnMod, "%");
//        setEqualsClickListener(binding.btnEqual);
//        setBackspaceClickListener(binding.btnBackspace);
//        setClearClickListener(binding.btnCancle);
//        setChangeClickListener(binding.btnChangePosNeg);
        this.init();

    }

    void init() {
        checkList = new ArrayList<>();
        operatorStack = new Stack<>();
        infixList = new ArrayList<>();
        postfixList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    int getWeight(String operator) {
        int weight = 0;
        switch (operator) {
            case "X":
            case "/":
                weight = 5;
                break;
            case "%":
                weight = 3;
                break;
            case "+":
            case "-":
                weight = 1;
                break;
        }
        return weight;
    }

    String calculate(String num1, String num2, String op) {
        double first = Double.parseDouble(num1);
        double second = Double.parseDouble(num2);
        double result = 0.0;
        try {
            switch (op) {
                case "X":
                    result = first * second;
                    break;
                case "/":
                    result = first / second;
                    break;
                case "%":
                    result = first % second;
                    break;
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;
            }
        } catch (Exception e) {
            makeToast("연산할 수 없습니다.");
        }
        return String.valueOf(result);
    }

    void addNumber(String str) {
        checkList.add(1); // 숫자가 들어왔는지 체크리스트에 표시
        txtExpression.append(str); // UI
    }
    boolean isNumber(String str) {
        boolean result = true;
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
    void infixToPostfix() {
        for (String item : infixList) {
            if(isNumber(item)) postfixList.add(String.valueOf(Double.parseDouble(item)));
            else {
                if(operatorStack.isEmpty()) operatorStack.push(item);
                else {
                    if (getWeight(operatorStack.peek()) >= getWeight(item)) postfixList.add(operatorStack.pop());
                    operatorStack.push(item);
                }
            }
        }
        while (!operatorStack.isEmpty()) postfixList.add(operatorStack.pop());
    }
    void result() {
        int i =0;
        infixToPostfix();
        while (postfixList.size() != 1) {
            if (!isNumber(postfixList.get(i))){
                postfixList.add(i - 2, calculate(postfixList.remove(i - 2), postfixList.remove(i - 2), postfixList.remove(i - 2)));
                i = -1;
            }
            i++;
        }
        txtResult.setText(postfixList.remove(0));
        infixList.clear();
    }
    void addDot(String str) {
        if(checkList.isEmpty()) {
            makeToast(". 을 사용할 수 없습니다.");
            return ;
        } else if (checkList.get(checkList.size() - 1) != -1) {
            makeToast(". 을 사용할 수 없습니다.");
            return ;
        }
        for(int i=checkList.size() - 2; i>=0; i--) {
            int check = checkList.get(i);
            if(check == 2) {
                makeToast(". 을 사용할 수 없습니다.");
                return ;
            }
            if(check == 0) break;
            if(check == 1) continue;
        }
        checkList.add(2);
        txtExpression.append(str);
    }

    void addOperator(String str) {
        try {
            if(checkList.isEmpty()) {
                makeToast("연산자가 올 수 없습니다.");
                return ;
            } else if(checkList.get(checkList.size() - 1) == 0 && checkList.get(checkList.size() - 1) == 2) {
                makeToast("연산자가 올 수 없습니다.");
                return ;
            }
            checkList.add(0);
            txtExpression.append(" " + str + " ");
        } catch (Exception e) {
            Log.e("addOperator", e.toString());
        }
    }

    public void buttonClick(View v) {
        if (!checkList.isEmpty() && checkList.get(checkList.size() - 1) == -1) {
            txtExpression.setText(txtResult.getText().toString());
            checkList.clear();
            checkList.add(1);
            checkList.add(2);
            checkList.add(1);
            txtResult.setText("");
        }
        if(v.getId() == R.id.btn_one) {
            addNumber("1");
        } else if (v.getId() == R.id.btn_two) {
            addNumber("2");
        } else if (v.getId() == R.id.btn_three) {
            addNumber("3");
        } else if (v.getId() == R.id.btn_four) {
            addNumber("4");
        } else if (v.getId() == R.id.btn_five) {
            addNumber("5");
        } else if (v.getId() == R.id.btn_six) {
            addNumber("6");
        } else if (v.getId() == R.id.btn_seven) {
            addNumber("7");
        } else if (v.getId() == R.id.btn_eight) {
            addNumber("8");
        } else if (v.getId() == R.id.btn_nine) {
            addNumber("9");
        } else if (v.getId() == R.id.btn_zero) {
            addNumber("0");
        } else if (v.getId() == R.id.btn_dot) {
            addDot(".");
        } else if (v.getId() == R.id.btn_div) {
            addOperator("/");
        } else if (v.getId() == R.id.btn_mul) {
            addOperator("*");
        } else if (v.getId() == R.id.btn_mod) {
            addOperator("%");
        } else if (v.getId() == R.id.btn_add) {
            addOperator("+");
        } else if (v.getId() == R.id.btn_sub) {
            addOperator("-");
        }
    }
    public void equalClick(View v) {
        if(txtExpression.length()== 0) return;
        if(checkList.get(checkList.size() - 1) != -1) {
            makeToast("숫자를 제대로 입력해주세요");
            return;
        }
        Collections.addAll(infixList, txtExpression.getText().toString().split(" "));
        checkList.add(-1);
        result();
    }
    public void clearClick (View v) {
        infixList.clear();
        postfixList.clear();
        txtExpression.setText("");
        txtResult.setText("");
        operatorStack.clear();
        checkList.clear();
    }
    public void deleteClick (View v) {
        if(txtExpression.length() != 0) {
            checkList.remove(checkList.size() - 1);
            String[] ex = txtExpression.getText().toString().split(" ");
            List<String> li = new ArrayList<>();
            Collections.addAll(li, ex);
            li.remove(li.size() - 1);
            if(li.size() > 0 && !isNumber(li.get(li.size() - 1))){
                li.add(li.remove(li.size() - 1) + " ");
            }
            txtExpression.setText(TextUtils.join(" ", li));
        }
        txtResult.setText("");
    }



//    // 숫자, .버튼 클릭시 함수
//    private void setButtonClickListener(Button button, final String value) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (value.matches("[0-9]") || value.equals(".")) {
//                    handleNumericInput(value);
//                }
//            }
//        });
//    }
//
//    // 연산자 클릭 함수
//    private void setOperatorClickListener(Button button, final String operator) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleOperatorClick(operator);
//            }
//        });
//    }
//
//    private void setEqualsClickListener(Button button) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleEqualsClick();
//            }
//        });
//    }
//
//    private void setBackspaceClickListener(Button button) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentInput.toString().isEmpty()) {
//                    return;
//                } else if (currentInput.toString().length() == 1) {
//                    currentInput.deleteCharAt(currentInput.length() - 1);
//                    binding.numInput.setText("0");
//                    binding.numResult.setText("0");
//                    return;
//                }
//                // 현재 입력된 문자열에서 마지막 한 글자 제거
//                currentInput.deleteCharAt(currentInput.length() - 1);
//                // 수정된 문자열을 numInput에 설정
//                binding.numInput.setText(currentInput.toString());
//                binding.numResult.setText(currentInput.toString());
//            }
//        });
//    }
//
//    private void setChangeClickListener(Button button) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentInput.length() > 0) {
//                    double currentValue = Double.parseDouble(currentInput.toString());
//                    double newValue = -currentValue;
//
//                    if (newValue % 1 == 0) {
//                        int newIntValue = (int) newValue;
//                        currentInput.setLength(0);
//                        appendToCurrentInput(String.valueOf(newIntValue));
//                    } else {
//                        currentInput.setLength(0);
//                        appendToCurrentInput(String.valueOf(newValue));
//                    }
//
//
//                }
//            }
//        });
//    }
//
//    private void setClearClickListener(Button button) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearCurrentInput();
//                clearCumulativeInput();
//                currentOperator = "";
//                binding.numInput.setText("0");
//                binding.numResult.setText("0");
//            }
//        });
//    }
//
//    private void handleNumericInput(String value) {
//        if (value.equals(".") && currentInput.length() == 0) {
//            makeToast("숫자를 먼저 입력하세요");
//        } else if (value.equals(".") && currentInput.toString().contains(".")) {
//            makeToast("소수점은 한 번만 입력 가능합니다");
//        } else {
//            appendToCurrentInput(value);
//        }
//
//    }
//
//    private void handleOperatorClick(String operator) {
//        if (currentInput.toString().isEmpty()) {
//            makeToast("숫자를 먼저 입력하세요");
//            return;
//        }
//        if (currentOperator.equals("")) {
//            appendToCumulativeInput(currentInput.toString());
//            clearCurrentInput();
//            currentOperator = operator;
//        } else {
//            handleEqualsClick();
//        }
//
//    }
//
//    private void handleEqualsClick() {
//        if (currentInput.toString().isEmpty()) {
//            makeToast("숫자를 먼저 입력하세요");
//            return;
//        }
//        if (currentOperator.equals("")) {
//            return;
//        }
//        double result = performCalculation(
//                Double.parseDouble(cumulativeInput.toString()),
//                Double.parseDouble(currentInput.toString()),
//                currentOperator
//        );
//        if (result % 1.0 == 0) {
//            int intResult = (int) result;
//            binding.numResult.setText(String.valueOf(intResult));
//            binding.numInput.setText(String.valueOf(intResult));
//            clearCurrentInput();
//            appendToCurrentInput(String.valueOf(intResult));
//            clearCumulativeInput();
//            currentOperator = "";
//        } else {
//            binding.numResult.setText(String.valueOf(result));
//            binding.numInput.setText(String.valueOf(result));
//            clearCurrentInput();
//            appendToCurrentInput(String.valueOf(result));
//            clearCumulativeInput();
//            currentOperator = "";
//        }
//
//
//    }
//
//    private double performCalculation(double operand1, double operand2, String operator) {
//        switch (operator) {
//            case "+":
//                return operand1 + operand2;
//            case "-":
//                return operand1 - operand2;
//            case "*":
//                return operand1 * operand2;
//            case "/":
//                // Handle division by zero
//                if (operand2 != 0) {
//                    return operand1 / operand2;
//                } else {
//                    makeToast("0으로 나눌 수 없습니다");
//                    return operand1;
//                }
//            case "%":
//                // Handle division by zero
//                if (operand2 != 0) {
//                    return operand1 % operand2;
//                } else {
//                    makeToast("0으로 나머지 연산 할 수 없습니다");
//                    return operand1; //
//                }
//            default:
//                makeToast("잘못된 연산자입니다");
//                return Double.NaN;
//        }
//    }
//
//
//    private void appendToCurrentInput(String value) {
//        currentInput.append(value);
//        binding.numInput.setText(currentInput.toString());
//        binding.numResult.setText(currentInput.toString());
//    }
//
//    private void appendToCumulativeInput(String value) {
//        cumulativeInput.append(value);
//        binding.numResult.setText(cumulativeInput.toString());
//    }
//
//    private void clearCurrentInput() {
//        currentInput.setLength(0);
//    }
//
//    private void clearCumulativeInput() {
//        cumulativeInput = new StringBuilder();
//    }

    // 토스트 메시지 함수
    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}