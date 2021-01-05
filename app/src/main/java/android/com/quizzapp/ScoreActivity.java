package android.com.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        TextView txtScore = findViewById(R.id.score);
        ImageView imgScore = findViewById(R.id.score_img);
        float score = getIntent().getFloatExtra("score",0);
        txtScore.setText(score+" %");
        if ( score < 50){
            imgScore.setImageDrawable(getResources().getDrawable(R.drawable.bad));
        }
    }
}