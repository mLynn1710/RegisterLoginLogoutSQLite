package marlyn.registerloginlogoutsqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Welcome extends AppCompatActivity {

    ImageView disimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txtname = (TextView) findViewById(R.id.txt_success_name);
        disimage = (ImageView) findViewById(R.id.imgclick);
        Button btnlogout = (Button) findViewById(R.id.logout);

        Intent intent = getIntent();

        String loginName = intent.getStringExtra("USERNAME");
        txtname.setText(loginName);

    }

    public void tologout (View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Welcome.this);
        builder.setTitle("LOGOUT");
        builder.setMessage("Do you want to logout ??");
        builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
