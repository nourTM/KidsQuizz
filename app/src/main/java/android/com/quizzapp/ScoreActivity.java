package android.com.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        float score = getIntent().getFloatExtra("score",0);
        final LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(param);
        view.setPadding(10,10,10,10);
        view.setBackgroundColor(Color.WHITE);
        view.setOrientation(LinearLayout.VERTICAL);
        ImageView emoji = new ImageView(this);
        emoji.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(300),dpToPx(300)));
        view.addView(emoji);
        TextView text = new TextView(this);
        text.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(300), ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setGravity(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(30);
        if ( score < 50){
            emoji.setImageResource(R.drawable.bad);
            text.setText("Try Again you will grow your knowledge"+score+" %" );
        }else{
            emoji.setImageResource(R.drawable.great);
            text.setText("You did a great Job :) "+score+" %" );
        }
        text.setTextColor(Color.parseColor("#16c79a"));

        //text.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(text);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
        TextView scoreText = findViewById(R.id.score);
        scoreText.setText(score+" %");
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}