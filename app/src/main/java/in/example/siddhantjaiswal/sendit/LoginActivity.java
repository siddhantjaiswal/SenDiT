package in.example.siddhantjaiswal.sendit;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.ServerProtocol;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE = 1;

    static String number = "9098320474";
    RelativeLayout activity_login1;
    private FirebaseListAdapter<ChatMessage> adapter;
    FloatingActionButton fab;
    class C02553 implements OnClickListener {
        C02553() {
        }

        public void onClick(View view) {
            EditText input = (EditText) LoginActivity.this.findViewById(R.id.input);
            if (input.getText().toString().trim().length() != 0) {
                Bundle udhrka = getIntent().getExtras();
                String email = udhrka.getString("email");
                //DatabaseReference dbrf= FirebaseDatabase.getInstance().getReference().child(number).push();
//                FirebaseDatabase.getInstance().getReference().child(email).push().setValue(email);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.aboutus) {
            startActivity(new Intent(this, AboutUs.class));
        }
        if (item.getItemId() == R.id.theme) {
            selectImage();
        }
        if (item.getItemId() == R.id.Settings) {
            startActivity(new Intent(this, Settings.class));
        }
        if (item.getItemId() == R.id.call) {
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login1);
        getWindow().setBackgroundDrawableResource(R.drawable.nature1);
        this.activity_login1 = (RelativeLayout) findViewById(R.id.activity_login1);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("sendit-16aa6/");
//        mDatabase.child("users").setValue("hi there this is a check msg");
        Bundle udhrka = getIntent().getExtras();
        if (udhrka != null) {
            String siddhant = udhrka.getString("siddhant");
            number = udhrka.getString("number");
            String emaill = udhrka.getString("email");
            Toast.makeText(this,emaill,Toast.LENGTH_LONG).show();
            setTitle(" " + siddhant);
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.AppTheme).build(), SIGN_IN_REQUEST_CODE);
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            displayChatMessage();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.logo);
            mBuilder.setContentTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            mBuilder.setContentText("Team SenDiT Welcomess You!!");
            Intent resultIntent = new Intent(this, LoginActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(LoginActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            mBuilder.setContentIntent(stackBuilder.getPendingIntent(0, 134217728));
            ((NotificationManager) getSystemService("notification")).notify(resultIntent.toString().length(), mBuilder.build());
        }
        this.fab.setOnClickListener(new C02553());

    }

    private void displayChatMessage() {
        try {
            ListView listOfMessage = (ListView) findViewById(R.id.list_of_message);
            this.adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
                protected void populateView(View v, ChatMessage model, int position) {
                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                    TextView messageText2 = (TextView) v.findViewById(R.id.message_text2);
                    String messageTxt = model.getMessageText();
                    String messageUsr = model.getMessageUser();
                    messageTime.setText(DateFormat.format("dd-MM-yy (hh:mm)", model.getMessageTime()));
                    String qwerty = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0];
                    messageUser.setText(messageUsr);
                    if (messageUser.getText().toString().trim().equals(qwerty.toString().trim())) {
                        messageText2.setText(messageTxt);
                        messageText2.setBackgroundResource(R.drawable.messageround1);
                        messageText2.setPadding(50, 10, 30, 20);
                        messageText2.setVisibility(View.VISIBLE);
                        messageText.setVisibility(View.INVISIBLE);
                        return;
                    }
                    messageText.setBackgroundResource(R.drawable.messageround2);
                    messageText.setText(messageTxt);
                    messageText.setPadding(20, 10, 50, 20);
                    messageText.setVisibility(View.VISIBLE);
                    messageText2.setVisibility(View.INVISIBLE);
                }
            };
            listOfMessage.setAdapter(this.adapter);
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public void selectImage() {
        try {final CharSequence[] options = new CharSequence[]{
//                "Kiss","Solo",
                "Love",  "Nature",
//                "Nature2",
                "Nature3","Nature4",
//                "Space" ,"Space2",
//                "Space3",
                "Art",
//                "Art2", "Art3",  "Art4",
                "Default", "Choose from Gallery", "Cancel"};
            Builder builder = new Builder(this);
            builder.setTitle((CharSequence) "Choose Theme!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

//                    if (options[item].equals("Kiss")) {
//
//                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.kiss);
//                    }
//                    else if (options[item].equals("Solo")) {
//
//                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space4);
//                    }
                    if (options[item].equals("Love")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.love);
                    }
                    else if (options[item].equals("Nature")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.nature1);
                    }
//                    else if (options[item].equals("Nature2")) {
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space10);
//                    }
                    else if (options[item].equals("Nature3")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space8);
                    }
                    else if (options[item].equals("Nature4")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space9);
                    }
//                    else if (options[item].equals("Space")) {
//                         LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space1);
//                    }
//                    else if (options[item].equals("Space2")) {
//                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space2);
//                    }
//                    else if (options[item].equals("Space3")) {
//                       LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space3);
//                    }
                    else if (options[item].equals("Art")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space3);
                    }
//                    else if (options[item].equals("Art2")) {
//                       LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space5);
//                    }
//                    else if (options[item].equals("Art4")) {
//                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space11);
//                    }
//                    else if (options[item].equals("Art3")) {
//                      LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space6);
//                    }
                    else if (options[item].equals("Default")) {
                        LoginActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.lite);
                    }
                    else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra("crop", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                        intent.putExtra("aspectX", 0);
                        intent.putExtra("aspectY", 0);
                        intent.putExtra("outputX", Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                        intent.putExtra("outputY", 300);
                        LoginActivity.this.startActivityForResult(intent, 2);
                    }
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileNotFoundException e;
        String[] filePath;
        Cursor c;
        String picturePath;
        IOException e2;
        Exception e3;
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == SIGN_IN_REQUEST_CODE) {
                if (requestCode == -1) {
                    Snackbar.make(this.activity_login1, (CharSequence) "Succssfully Signed In", -1).show();
                    displayChatMessage();
                } else {
                    Snackbar.make(this.activity_login1, (CharSequence) "We couldn't sign You in Please try Again Later", -1).show();
                    finish();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == -1) {
                if (requestCode == 1) {
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), new Options());
                        String path = Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                        f.delete();
                        try {
                            OutputStream fileOutputStream = new FileOutputStream(new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"));
                            OutputStream outputStream;
                            try {
                                bitmap.compress(CompressFormat.JPEG, 85, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                outputStream = fileOutputStream;
                            } catch (FileNotFoundException e4) {
                                e = e4;
                                outputStream = fileOutputStream;
                                e.printStackTrace();
                                if (requestCode != 2) {
                                    filePath = new String[]{"_data"};
                                    c = getContentResolver().query(data.getData(), filePath, null, null, null);
                                    c.moveToFirst();
                                    picturePath = c.getString(c.getColumnIndex(filePath[0]));
                                    c.close();
                                    getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                                }
                            } catch (IOException e5) {
                                e2 = e5;
                                outputStream = fileOutputStream;
                                e2.printStackTrace();
                                if (requestCode != 2) {
                                    filePath = new String[]{"_data"};
                                    c = getContentResolver().query(data.getData(), filePath, null, null, null);
                                    c.moveToFirst();
                                    picturePath = c.getString(c.getColumnIndex(filePath[0]));
                                    c.close();
                                    getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                                }
                            } catch (Exception e6) {
                                e3 = e6;
                                outputStream = fileOutputStream;
                                e3.printStackTrace();
                                if (requestCode != 2) {
                                    filePath = new String[]{"_data"};
                                    c = getContentResolver().query(data.getData(), filePath, null, null, null);
                                    c.moveToFirst();
                                    picturePath = c.getString(c.getColumnIndex(filePath[0]));
                                    c.close();
                                    getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                                }
                            }
                        } catch (FileNotFoundException e7) {
                            e = e7;
                            e.printStackTrace();
                            if (requestCode != 2) {
                                filePath = new String[]{"_data"};
                                c = getContentResolver().query(data.getData(), filePath, null, null, null);
                                c.moveToFirst();
                                picturePath = c.getString(c.getColumnIndex(filePath[0]));
                                c.close();
                                getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                            }
                        }  catch (Exception e9) {
                            e3 = e9;
                            e3.printStackTrace();
                            if (requestCode != 2) {
                                filePath = new String[]{"_data"};
                                c = getContentResolver().query(data.getData(), filePath, null, null, null);
                                c.moveToFirst();
                                picturePath = c.getString(c.getColumnIndex(filePath[0]));
                                c.close();
                                getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                            }
                        }
                    } catch (Exception e32) {
                        e32.printStackTrace();
                    }
                }
                if (requestCode != 2) {
                    filePath = new String[]{"_data"};
                    c = getContentResolver().query(data.getData(), filePath, null, null, null);
                    c.moveToFirst();
                    picturePath = c.getString(c.getColumnIndex(filePath[0]));
                    c.close();
                    getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                }
            }
        } catch (Exception e10) {
        }
    }
}
