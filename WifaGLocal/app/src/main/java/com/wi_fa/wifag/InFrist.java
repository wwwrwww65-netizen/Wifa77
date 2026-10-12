package com.wi_fa.wifag;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class InFrist extends AppCompatActivity {
    private static long SLEEP_TIME = 2;    // Sleep for some time
    myDbAdapter helper;
    DbAccessp helperap;
    CountDownTimer waitTimer;
    int wificon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
        setContentView(R.layout.activity_in_frist);
        try {
            waitTimer = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    IntentLauncher launcher = new IntentLauncher();
                    launcher.start();
                }
            }.start();
        }
        catch (Exception e){}





    }
    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                waitTimer.cancel();
                Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
                //Log.e(TAG, e.getMessage());
            }
            // Start main activity
            Intent intent = new Intent(InFrist.this, LoginActivity.class);
            InFrist.this.startActivity(intent);
            InFrist.this.finish();
        }
    }
}
