package in.example.siddhantjaiswal.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class Main2Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 1100;
    private ProgressBar spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main2);
        int myTimer = 1000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, myTimer);
    }
    }
