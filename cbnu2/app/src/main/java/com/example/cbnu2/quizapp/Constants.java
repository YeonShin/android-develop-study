package com.example.cbnu2.quizapp;

import com.example.cbnu2.R;
import java.util.ArrayList;

public class Constants {
    static final String USER_NAME = "user_name";
    static final String TOTAL_QUESTIONS = "total_questions";
    static final String CORRECT_ANSWERS = "correct_answers";

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();

        Question que1 = new Question(
                1, "1What country does this flag belong to?",
                R.drawable.ic_flag_of_argentina,
                "Argentina", "Australia",
                "Armenia", "Austria", 1
        );
        questionsList.add(que1);

        // 2
        Question que2 = new Question(
                2, "What country does this flag belong to?",
                R.drawable.ic_flag_of_australia,
                "Angola", "Austria",
                "Australia", "Armenia", 3
        );

        questionsList.add(que2);

        // 3
        Question que3 = new Question(
                3, "What country does this flag belong to?",
                R.drawable.ic_flag_of_brazil,
                "Belarus", "Belize",
                "Brunei", "Brazil", 4
        );

        questionsList.add(que3);

        // 4
        Question que4 = new Question(
                4, "What country does this flag belong to?",
                R.drawable.ic_flag_of_belgium,
                "Bahamas", "Belgium",
                "Barbados", "Belize", 2
        );

        questionsList.add(que4);

        // 5
        Question que5 = new Question(
                5, "What country does this flag belong to?",
                R.drawable.ic_flag_of_fiji,
                "Gabon", "France",
                "Fiji", "Finland", 3
        );

        questionsList.add(que5);

        // 6
        Question que6 = new Question(
                6, "What country does this flag belong to?",
                R.drawable.ic_flag_of_germany,
                "Germany", "Georgia",
                "Greece", "none of these", 1
        );

        questionsList.add(que6);

        // 7
        Question que7 = new Question(
                7, "What country does this flag belong to?",
                R.drawable.ic_flag_of_denmark,
                "Dominica", "Egypt",
                "Denmark", "Ethiopia", 3
        );

        questionsList.add(que7);

        // 8
        Question que8 = new Question(
                8, "What country does this flag belong to?",
                R.drawable.ic_flag_of_india,
                "Ireland", "Iran",
                "Hungary", "India", 4
        );

        questionsList.add(que8);

        // 9
        Question que9 = new Question(
                9, "What country does this flag belong to?",
                R.drawable.ic_flag_of_new_zealand,
                "Australia", "New Zealand",
                "Tuvalu", "United States of America", 2
        );

        questionsList.add(que9);

        // 10
        Question que10 = new Question(
                10, "What country does this flag belong to?",
                R.drawable.ic_flag_of_kuwait,
                "Kuwait", "Jordan",
                "Sudan", "Palestine", 1
        );

        questionsList.add(que10);

        return questionsList;

    }
}
