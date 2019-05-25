package in.example.siddhantjaiswal.sendit;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Settings extends AppCompatActivity {

    ImageView b;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_settings);
        setTitle("Settings");
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://sendit-641e7.appspot.com").child("mountains.jpg");
        try {
            final File localFile = File.createTempFile("mountain", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    ImageView sid = (ImageView) findViewById(R.id.sid);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    //bitmap.setWidth(200);
                    sid.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e ) {}



            b=(ImageView)findViewById(R.id.sid);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();

                }
            });

            TextView name = (TextView)findViewById(R.id.textView);
            name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }


    private void selectImage() {
        final CharSequence[] options = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) "Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent intent;
                if (options[item].equals("Take Photo")) {
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
                    Settings.this.startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                    intent.putExtra("crop", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    intent.putExtra("aspectX", 50);
                    intent.putExtra("aspectY", 50);
                    intent.putExtra("outputX", 500);
                    intent.putExtra("outputY", 500);
                    Settings.this.startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileNotFoundException e;
        IOException e2;
        Exception e3;
        ImageView viewImage = (ImageView) findViewById(R.id.sid);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        Bitmap bitmap1;
        if (requestCode == 1) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            try {
                bitmap1 = BitmapFactory.decodeFile(f.getAbsolutePath(), new Options());
                viewImage.setImageBitmap(bitmap1);
                String path = Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                f.delete();
                try {
                    OutputStream outFile = new FileOutputStream(new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    OutputStream outputStream;
                    try {
                        bitmap1.compress(CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                        outputStream = outFile;
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        outputStream = outFile;
                        e.printStackTrace();
                    } catch (IOException e5) {
                        e2 = e5;
                        outputStream = outFile;
                        e2.printStackTrace();
                    } catch (Exception e6) {
                        e3 = e6;
                        outputStream = outFile;
                        e3.printStackTrace();
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                    e.printStackTrace();
                } catch (IOException e8) {
                    e2 = e8;
                    e2.printStackTrace();
                } catch (Exception e9) {
                    e3 = e9;
                    e3.printStackTrace();
                }
            } catch (Exception e32) {
                e32.printStackTrace();
            }
        } else if (requestCode == 2) {

            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
                viewImage.setImageBitmap(photo);
                viewImage.setDrawingCacheEnabled(true);
                viewImage.buildDrawingCache();
                Bitmap bitmap = viewImage.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] data1 = baos.toByteArray();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child("mountains.jpg");
                StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
                mountainsRef.getName().equals(mountainImagesRef.getName());
                UploadTask uploadTask = mountainsRef.putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                });
            }}
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

    public void save(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void help(View view) {
        try {
            final CharSequence[] options = new CharSequence[]{"Email Us", "Cancel"};
            Builder builder = new Builder(this);
            builder.setTitle((CharSequence) "NEED HELP");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Email Us")) {
                        Log.i("Send email", "");
                        String[] TO = new String[]{"siddhantjai9098@gmail.com"};
                        String[] CC = new String[]{""};
                        Intent emailIntent = new Intent("android.intent.action.SEND");
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra("android.intent.extra.EMAIL", TO);
                        emailIntent.putExtra("android.intent.extra.CC", CC);
                        emailIntent.putExtra("android.intent.extra.SUBJECT", "SenDiT");
                        emailIntent.putExtra("android.intent.extra.TEXT", "");
                        try {
                            Settings.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            Settings.this.finish();
                            Log.i("Finished sending email.", "");
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(Settings.this, "There is no email client installed.", 0).show();
                        }
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
        }
    }

    public void status_user(View view) {
        startActivity(new Intent(this, Status_user.class));
        finish();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MyString", ((TextView) findViewById(R.id.textView)).getText().toString());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ((TextView) findViewById(R.id.textView)).setText(savedInstanceState.getString("MyString"));
    }
}
