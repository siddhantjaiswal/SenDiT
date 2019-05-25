package in.example.siddhantjaiswal.sendit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class AboutUs extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_about_us);
        setTitle("About Us");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            onBackPressed();
        }
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
