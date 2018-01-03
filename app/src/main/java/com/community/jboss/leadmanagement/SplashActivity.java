package com.community.jboss.leadmanagement;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.community.jboss.leadmanagement.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.launcher_logo)
    ImageView launcher_logo;
    @BindView(R.id.launcher_text)
    TextView launcher_text;
    @BindView(R.id.launcher_progress)
    ProgressBar launcher_progress;
    Animation bottomUp;
    Animation fade_in_text;
    Animation fade_in_transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        fade_in_text = AnimationUtils.loadAnimation(this, R.anim.fade_in_text);
        fade_in_transition = AnimationUtils.loadAnimation(this,R.anim.fade_in_transition);
        launcher_progress.setRotation(360);
        launcher_progress.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in_transition_more));
        launcher_logo.setAnimation(fade_in_transition);
        launcher_logo.setAnimation(bottomUp);
        launcher_text.setAnimation(fade_in_text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_transition, R.anim.fade_out_transition);
                finish();
            }
        },3500);
    }
}
