package android.com.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private int score;
    private String category;
    private int currentQst = 0;
    private Map<Integer, String> reponses;
    ArrayList<Quizz> quizz;
    RelativeLayout root;
    LinearLayout reponse;
    TextView qst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = getIntent().getStringExtra("category");
        reponse = findViewById(R.id.reponse);
        /*  preparation of math quizz */
        ArrayList<Quizz> mathQuizz = new ArrayList<>();
        mathQuizz.add(new Quizz("How much is 1 + 2?", new ArrayList<String>(Arrays.asList("0", "5", "3", "7")), "3", QuestionType.ONE));
        mathQuizz.add(new Quizz("What is the next prime number after 7?", new ArrayList<String>(Arrays.asList("8", "15", "11", "21")), "11", QuestionType.ONE));
        mathQuizz.add(new Quizz("Which of those are natural numbers? ", new ArrayList<String>(Arrays.asList("4", "-4", "0", "1")), "4,0,1,", QuestionType.MULTIPLE));
        mathQuizz.add(new Quizz(" How many sides does a nonagon have?", new ArrayList<String>(Arrays.asList("8", "9", "5", "0")), "9", QuestionType.ONE));

        /*  preparation of geography quizz */
        ArrayList<Quizz> hisGeoQuizz = new ArrayList<>();
        hisGeoQuizz.add(new Quizz("How many continents are there?", new ArrayList<String>(Arrays.asList("9", "5", "3", "7")), "7", QuestionType.ONE));
        hisGeoQuizz.add(new Quizz("What is the largest country in the world?", new ArrayList<String>(Arrays.asList("Algeria", "Russia", "China", "USA")), "Russia", QuestionType.ONE));
        hisGeoQuizz.add(new Quizz("What country has the largest population in the world?", new ArrayList<String>(Arrays.asList("Algeria", "Russia", "China", "USA")), "China", QuestionType.INPUT));

        reponses = new HashMap<>();
        final Context context = this;
        quizz = (category.equals("geo")) ? hisGeoQuizz : mathQuizz;
        root = findViewById(R.id.root);
        qst = findViewById(R.id.quizz_qst);
        final TextView index = findViewById(R.id.index);
        if (quizz.size() != 0) {
            generateQst();
            index.setText("1 / " + quizz.size());
        }


        final Button next = findViewById(R.id.next);
        final Button prev = findViewById(R.id.prev);
        prev.setVisibility(View.INVISIBLE);

        final Button submit = new Button(this);
        submit.setText("Submit");
        submit.setBackgroundColor(Color.parseColor("#16c79a"));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(10, 10, 10, 10);
        submit.setLayoutParams(params);


        submit.setVisibility(View.INVISIBLE);
        root.addView(submit);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQst++;
                prev.setVisibility(View.VISIBLE);
                final int size = quizz.size();
                index.setText((currentQst + 1) + " / " + size);
                reponse.removeAllViews();
                generateQst();
                if (currentQst == size - 1) {
                    next.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (Integer i : reponses.keySet()) {
                                if (reponses.get(i).equals(quizz.get(i).getCorrectAnswer()))
                                    score++;
                            }
                            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                            intent.putExtra("score", ((float) score / size) * 100);
                            startActivity(intent);
                            // if never the user click the back button
                            score = 0;
                            reponses.clear();
                        }
                    });
                }
            }

        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here it does not matter to put invisible or gone the space will not affect the layout
                submit.setVisibility(View.INVISIBLE);
                currentQst--;

                next.setVisibility(View.VISIBLE);
                int size = quizz.size();
                index.setText((currentQst + 1) + " / " + size);
                reponse.removeAllViews();
                generateQst();
                if (currentQst == 0) {
                    prev.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    private void generateOneQst() {
        RadioGroup group = new RadioGroup(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(40, 40, 40, 40);
        params.addRule(RelativeLayout.BELOW, R.id.quizz_qst);
        group.setLayoutParams(params);

        for (int i = 0; i < quizz.get(currentQst).getReponses().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTag(i);
            radioButton.setText(quizz.get(currentQst).getReponses().get(i));
            radioButton.setId(i);
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            radioButton.setPadding(40, 40, 40, 40);
            if (reponses.containsKey(currentQst)) {
                if (reponses.get(currentQst).equals(radioButton.getText()))
                    radioButton.setChecked(true);
            }
            group.addView(radioButton);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                reponses.put(currentQst, ((RadioButton) findViewById(checkedId)).getText().toString());
            }
        });
        reponse.addView(group);
    }

    private void generateMultiQst() {
        LinearLayout linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.quizz_qst);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(40, 40, 40, 40);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < quizz.get(currentQst).getReponses().size(); i++) {
            CheckBox choice = new CheckBox(this);
            choice.setTag(i);
            choice.setText(quizz.get(currentQst).getReponses().get(i));
            choice.setTag(i);
            choice.setId(i);
            choice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            choice.setPadding(40, 40, 40, 40);
            if (reponses.containsKey(currentQst)) {
                String[] ss = reponses.get(currentQst).split(",");
                for (String s : ss) {
                    if (choice.getText().equals(s)) choice.setChecked(true);
                }
            }
            choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (reponses.containsKey(currentQst))
                            reponses.put(currentQst, reponses.get(currentQst).concat(buttonView.getText() + ","));
                        else reponses.put(currentQst, buttonView.getText() + ",");
                    } else {
                        reponses.put(currentQst, reponses.get(currentQst).replace(buttonView.getText() + ",", ""));
                    }
                }
            });
            linearLayout.addView(choice);
        }

        reponse.addView(linearLayout);

    }

    private void generateInputQst() {
        final EditText input = new EditText(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.quizz_qst);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        params.setMargins(40, 40, 40, 40);
        input.setLayoutParams(params);
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        input.setPadding(40, 40, 40, 40);
        input.setId((int) 5);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reponses.put(currentQst, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (reponses.containsKey(currentQst)) {
            input.setText(reponses.get(currentQst));
        }
        reponse.addView(input);
    }

    private void generateQst() {
        qst.setText(quizz.get(currentQst).getQuestion());
        if (quizz.get(currentQst).getType() == QuestionType.ONE) {
            generateOneQst();
        } else if (quizz.get(currentQst).getType() == QuestionType.MULTIPLE) {
            generateMultiQst();
        } else {
            generateInputQst();
        }

    }
}