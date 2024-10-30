package com.example.android_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private SeekBar seekBarSalary;
    private Button buttonSubmit;
    private TextView textViewResult;
    private CheckBox checkBoxExperience, checkBoxTeamwork, checkBoxTravel;
    private RadioGroup question1, question2, question3, question4, question5;
    private TextView textViewSalary;

    private int minAge = 21;
    private int maxAge = 40;
    private int minSalary = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        seekBarSalary = findViewById(R.id.seekBarSalary);
        textViewSalary = findViewById(R.id.textViewSalary);
        buttonSubmit = findViewById(R.id.buttonSubmitTest);
        textViewResult = findViewById(R.id.textViewResult);
        checkBoxExperience = findViewById(R.id.checkBoxExperience);
        checkBoxTeamwork = findViewById(R.id.checkBoxTeamSkills);
        checkBoxTravel = findViewById(R.id.checkBoxTravel);

        question1 = findViewById(R.id.radioGroupQuestion1);
        question2 = findViewById(R.id.radioGroupQuestion2);
        question3 = findViewById(R.id.radioGroupQuestion3);
        question4 = findViewById(R.id.radioGroupQuestion4);
        question5 = findViewById(R.id.radioGroupQuestion5);

        buttonSubmit.setOnClickListener(v -> {
            if (validateInput())
            {
                int score = calculateScore();
                displayResult(score);
            }
        });

        seekBarSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                int salary = minSalary + progress;
                textViewSalary.setText("Salary: " + salary + " USD");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    private boolean validateInput() {
        String name = editTextName.getText().toString();
        String ageStr = editTextAge.getText().toString();
        int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);

        if (name.isEmpty())
        {
            editTextName.setError("Enter full name");
            return false;
        }
        if (age < minAge || age > maxAge)
        {
            editTextAge.setError("Age should be between 21 and 40 years");
            return false;
        }

        return true;
    }

    private int calculateScore() {
        int score = 0;

        score += calculateQuestionScore(question1, R.id.radioButtonOption1q1);
        score += calculateQuestionScore(question2, R.id.radioButtonOption1q2);
        score += calculateQuestionScore(question3, R.id.radioButtonOption1q3);
        score += calculateQuestionScore(question4, R.id.radioButtonOption1q4);
        score += calculateQuestionScore(question5, R.id.radioButtonOption1q5);

        if (checkBoxExperience.isChecked()) score += 2;
        if (checkBoxTeamwork.isChecked()) score += 1;
        if (checkBoxTravel.isChecked()) score += 1;

        return score;
    }

    private int calculateQuestionScore(RadioGroup questionGroup, int correctAnswerId) {
        int selectedId = questionGroup.getCheckedRadioButtonId();
        if (selectedId == correctAnswerId) {
            return 2;
        }

        return 0;
    }

    private void displayResult(int score)
    {
        if (score >= 10)
        {
            textViewResult.setText("You passed the interview\nScore: " + score);
        }
        else
        {
            textViewResult.setText("Unfortunately, you did not pass the interview\nScore: " + score);
        }

        textViewResult.setVisibility(View.VISIBLE);
    }
}