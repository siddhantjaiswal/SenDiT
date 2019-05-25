package in.example.siddhantjaiswal.sendit;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.internal.ServerProtocol;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private static int f24a = 1;
    private ProductListAdapter adapter;
    boolean doubleBackToExitPressedOnce = false;
    private ListView lvProduct;
    private List<Product> mProductList;
    static int adg = 10;

    class C02581 implements OnItemClickListener {
        C02581() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String name = ((TextView) view.findViewById(R.id.tv_name)).getText().toString();
            String number = ((TextView) view.findViewById(R.id.tv_price)).getText().toString();
            String email = ((TextView) view.findViewById(R.id.tv_description)).getText().toString();

            Intent i = new Intent(view.getContext(), LoginActivity.class);
            i.putExtra("number", number);
            i.putExtra("siddhant", name);
            i.putExtra("email",email);
            MainActivity.this.startActivity(i);
        }
    }

    class C02592 implements Runnable {
        C02592() {
        }

        public void run() {
            MainActivity.this.doubleBackToExitPressedOnce = false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_womain, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        setTitle("SenDiT");

        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.lite);
        try {
            if (!(ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS"))) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 2);
            }
            this.lvProduct = (ListView) findViewById(R.id.listview_product);
            int f = 1;
            this.mProductList = new ArrayList();
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(Contacts.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext())
            {
                String email = "-";
                String phoneNumber = null;
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                Cursor emailCursor = resolver.query(Email.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                while (emailCursor.moveToNext())
                    {
                        email = emailCursor.getString(emailCursor.getColumnIndex("data1"));
                    }
                if (email != "-")
                {
                    String name = cursor.getString(cursor.getColumnIndex("display_name"));
                    Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                    while (phoneCursor.moveToNext())
                    {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex("data1"));
                    }
                    this.mProductList.add(new Product(Integer.toString(f), name, phoneNumber, email));
                    f++;
                }
            }
            this.adapter = new ProductListAdapter(getApplicationContext(), this.mProductList);
            this.lvProduct.setAdapter(this.adapter);
            this.lvProduct.setOnItemClickListener(new C02581());
        }
        catch (Exception e)
        {
            System.exit(1);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new C02592(), 2000);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.contact) {
            Intent intent = new Intent("android.intent.action.INSERT");
            intent.setType("vnd.android.cursor.dir/raw_contact");
            Toast.makeText(this, "Add contact with Email ID", 1).show();
            startActivity(intent);
        }
        if (item.getItemId() == R.id.aboutus) {
            startActivity(new Intent(this, AboutUs.class));
        }
        if (item.getItemId() == R.id.theme) {
            selectImage();
        }
        if (item.getItemId() == R.id.menu_sign_out)
        {

            final CharSequence[] options = {"Yes", "No"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Click Yes To SignOut");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    dialog.dismiss();
                    if (options[item].equals("Yes")) {
                        adg = 1;

                    } else if (options[item].equals("No")) {
                        adg = 0;
                        dialog.dismiss();
                    }

                }
            });builder.show();
        }
        if(item.getItemId()==R.id.Settings){
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.AppTheme).build(),1);

            }
            else {
                startActivity(new Intent(this, Settings.class));
            }
        }
        if (adg == 1) {

            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    LinearLayout relativeLayout =(LinearLayout) findViewById(R.id.qwerty);
                    Snackbar.make(relativeLayout, "You have Signed Out", Snackbar.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_login1);
                }
            });
            this.finish();
        }
        return true;
    }

    private void selectImage() {
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
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.kiss);
//                    }
//                    else if (options[item].equals("Solo")) {
//
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space4);
//                    }
                    if (options[item].equals("Love")) {
                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.love);
                    }
                    else if (options[item].equals("Nature")) {
                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.nature1);
                    }
//                    else if (options[item].equals("Nature2")) {
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space10);
//                    }
                    else if (options[item].equals("Nature3")) {
                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space8);
                    }
                    else if (options[item].equals("Nature4")) {
                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space9);
                    }
//                    else if (options[item].equals("Space")) {
//                         MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space1);
//                    }
//                    else if (options[item].equals("Space2")) {
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space2);
//                    }
//                    else if (options[item].equals("Space3")) {
//                       MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space3);
//                    }
                    else if (options[item].equals("Art")) {
                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space3);
                    }
//                    else if (options[item].equals("Art2")) {
//                       MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space5);
//                    }
//                    else if (options[item].equals("Art4")) {
//                        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space11);
//                    }
//                    else if (options[item].equals("Art3")) {
//                      MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.space6);
//                    }
                    else if (options[item].equals("Default")) {
                     MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.lite);
                    }
                    else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra("crop", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                        intent.putExtra("aspectX", 0);
                        intent.putExtra("aspectY", 0);
                        intent.putExtra("outputX", Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                        intent.putExtra("outputY", 300);
                        MainActivity.this.startActivityForResult(intent, 2);
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


            super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == 2) {
                  Toast.makeText(this,"This function will be available soon",Toast.LENGTH_SHORT).show();
                //    getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(picturePath)));
                }
            }
        }

