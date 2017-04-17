package marlyn.registerloginlogoutsqlite;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btnSignIn, btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);

        SharedPreferences settings = getSharedPreferences(MyPREFERENCES, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            startActivity(intent);
        }

    }

    public void signUp (View view){
        Intent intentSignUP = new Intent(MainActivity.this,
                SignUP.class);
        startActivity(intentSignUP);
    }

    public void signIn(View V) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        final EditText editTextUserName = (EditText) dialog
                .findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) dialog
                .findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String storedPassword = loginDataBaseAdapter
                        .getSinlgeEntry(userName);
                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this,
                            "Congrats: Login Successfull", Toast.LENGTH_LONG)
                            .show();
                    dialog.dismiss();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("userName", userName);
                    editor.putString("password", password);
                    editor.putString("logged", "logged");
                    editor.commit();

                    Intent main = new Intent(MainActivity.this, Welcome.class);
                    main.putExtra("USERNAME", userName);
                    startActivity(main);
                } else {
                    Toast.makeText(MainActivity.this,
                            "User Name or Password does not match",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_menu:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
