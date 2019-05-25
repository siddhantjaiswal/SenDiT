package in.example.siddhantjaiswal.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Status_user extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_status_user);
        setTitle("Status");
    }

    public void onClick(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView9)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick1(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView10)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick2(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView11)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick3(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView12)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick4(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView13)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick5(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView14)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick6(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView15)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick7(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView16)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void onClick8(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        String s = ((TextView) findViewById(R.id.textView17)).getText().toString();
        ((EditText) findViewById(R.id.edt)).setText(s);
        status.setText(s);
    }

    public void save_status(View view) {
        TextView status = (TextView) findViewById(R.id.status);
        EditText edt = (EditText) findViewById(R.id.edt);
        String s = edt.getText().toString();
        if (edt.getText().toString().trim().length() != 0) {
            status.setText(s);
            Intent i = new Intent(this, Settings.class);
            i.putExtra("hi", s);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this,"Status Can Not Be Empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        TextView status = (TextView) findViewById(R.id.status);
        status.setText(status.getText().toString());
    }
}
