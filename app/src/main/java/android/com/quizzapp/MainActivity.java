package android.com.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private int score;
    private String category;
    private int currentQst = 0;
    private Map<Integer,Integer> reponses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = getIntent().getStringExtra("category");

        /*  preparation of math quizz */
        final ArrayList<Quizz> mathQuizz = new ArrayList<>();
        mathQuizz.add( new Quizz("How much is 1 + 2?", new ArrayList<String>(Arrays.asList("0","5","3","7")),2));

        mathQuizz.add( new Quizz("What is the next prime number after 7?", new ArrayList<String>(Arrays.asList("8","15","11","21")),0));

        mathQuizz.add( new Quizz("True or false? -4 is a natural number? ", new ArrayList<String>(Arrays.asList("True","False")),1));

        mathQuizz.add( new Quizz(" How many sides does a nonagon have?", new ArrayList<String>(Arrays.asList("8","9","5","0")),1));

        /*  preparation of math quizz */
        final ArrayList<Quizz> hisGeoQuizz = new ArrayList<>();

        hisGeoQuizz.add( new Quizz("How many continents are there?", new ArrayList<String>(Arrays.asList("9","5","3","7")),3));

        hisGeoQuizz.add( new Quizz("What is the largest country in the world?", new ArrayList<String>(Arrays.asList("Algeria","Russia","China","USA")),1));

        hisGeoQuizz.add( new Quizz("What country has the largest population in the world?", new ArrayList<String>(Arrays.asList("Algeria","Russia","China","USA")),2));


        //reponses = new ArrayList<>((category.equals("geo"))?hisGeoQuizz.size():mathQuizz.size());
        reponses = new HashMap<>();
        final Context  context = this;

        final TextView textView = findViewById(R.id.quizz_qst);
        final TextView index = findViewById(R.id.index);
        final RadioGroup radioGroup = findViewById(R.id.choice);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(context,findViewById(checkedId).getTag().toString(),Toast.LENGTH_LONG).show();
                reponses.put(currentQst,Integer.valueOf(findViewById(checkedId).getTag().toString()));
            }
        });
        if (category.equals("geo")){
            textView.setText(hisGeoQuizz.get(0).getQuestion());
            for (int i = 0; i < hisGeoQuizz.get(currentQst).getReponses().size();i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setTag(i);
                radioButton.setText(hisGeoQuizz.get(0).getReponses().get(i));
                radioButton.setTag(i);
                radioButton.setId(i);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                radioButton.setPadding(40,40,40,40);
                radioGroup.addView(radioButton);
            }
            index.setText("1 / "+hisGeoQuizz.size());
        }else{
            textView.setText(mathQuizz.get(0).getQuestion());
            for (int i = 0; i < mathQuizz.get(currentQst).getReponses().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setTag(i);
                radioButton.setText(mathQuizz.get(0).getReponses().get(i));
                radioButton.setTag(i);
                radioButton.setId(i);
                radioButton.setPadding(40,40,40,40);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                radioGroup.addView(radioButton);
            }
            index.setText("1 / "+mathQuizz.size());
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
        params.setMargins(10,10,10,10);
        submit.setLayoutParams(params);

        RelativeLayout root = findViewById(R.id.root);
        submit.setVisibility(View.INVISIBLE);
        root.addView(submit);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQst++;

                prev.setVisibility(View.VISIBLE);
                final int size;
                if (category.equals("geo")) size = hisGeoQuizz.size();
                else size = mathQuizz.size();
                index.setText((currentQst+1)+" / " + size);
                if (currentQst == size-1) {
                    radioGroup.removeAllViews();
                    if (category.equals("geo")) {
                        textView.setText(hisGeoQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < hisGeoQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(hisGeoQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    } else {
                        textView.setText(mathQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < mathQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(mathQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    }
                    next.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,reponses.toString(),Toast.LENGTH_LONG).show();
                            for (Integer i : reponses.keySet()) {
                                if(category.equals("geo")) {
                                    if (reponses.get(i).equals(hisGeoQuizz.get(i).getCorrectAnswerIndex())) score ++;
                                }else {
                                    if (reponses.get(i).equals(mathQuizz.get(i).getCorrectAnswerIndex())) score ++;
                                }
                            }
                            Toast.makeText(context,String.valueOf(score),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
                            intent.putExtra("score", ((float)score/size)*100);
                            startActivity(intent);
                            score = 0;
                        }
                    });
                } else {

                    radioGroup.removeAllViews();
                    if (category.equals("geo")) {
                        textView.setText(hisGeoQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < hisGeoQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(hisGeoQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    } else {
                        textView.setText(mathQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < mathQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(mathQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    }
                }
                if (reponses.containsKey(currentQst)) radioGroup.check(reponses.get(currentQst));
            }

        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.INVISIBLE);
                currentQst--;

                next.setVisibility(View.VISIBLE);
                int size;
                if (category.equals("geo")) size = hisGeoQuizz.size();
                else size = mathQuizz.size();

                if (currentQst == 0) {
                    prev.setVisibility(View.INVISIBLE);
                    /*Button submit = new Button(context);
                    submit.setText("Submit");
                    submit.setBackgroundColor(Color.parseColor("#16c79a"));*/
                    index.setText((currentQst+1) + " / " + size);
                    radioGroup.removeAllViews();
                    if (category.equals("geo")) {
                        textView.setText(hisGeoQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < hisGeoQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(hisGeoQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setId(i);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    } else {
                        textView.setText(mathQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < mathQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(mathQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    }
                } else {
                    index.setText((currentQst+1) + " / " + size);
                    radioGroup.removeAllViews();
                    if (category.equals("geo")) {
                        textView.setText(hisGeoQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < hisGeoQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(hisGeoQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    } else {
                        textView.setText(mathQuizz.get(currentQst).getQuestion());
                        for (int i = 0; i < mathQuizz.get(currentQst).getReponses().size(); i++) {
                            RadioButton radioButton = new RadioButton(context);
                            radioButton.setText(mathQuizz.get(currentQst).getReponses().get(i));
                            radioButton.setTag(i);
                            radioButton.setId(i);
                            radioButton.setPadding(40,40,40,40);
                            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                            radioGroup.addView(radioButton);
                        }
                    }
                }
                if (reponses.containsKey(currentQst)) radioGroup.check(reponses.get(currentQst));
            }
        });
    }
}