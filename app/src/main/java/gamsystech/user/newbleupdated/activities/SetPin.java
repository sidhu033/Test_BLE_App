package gamsystech.user.newbleupdated.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.instruction_activity.InstructionActivity;
import gamsystech.user.newbleupdated.activities.login_activity.LoginActivity;
import gamsystech.user.newbleupdated.session.UserSessionManager;

public class SetPin extends AppCompatActivity
{
    private Button btnpin;
    private EditText pinview1;
    private TextView txtcreatepin;
    private String pin;
    private  UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        //initilization

        initilization();
        onclickmethods();

        if(!userSessionManager.loggedin())
        {
            //logout();
        }
        //insttiation

    }
    //oncreate end


    private void initilization()
    {
        btnpin = findViewById(R.id.btnpin);
        pinview1 = findViewById(R.id.pinview1);
        txtcreatepin = findViewById(R.id.txtcreatepin);

        userSessionManager = new UserSessionManager(this);

        pin = pinview1.getText().toString();
    }

    private void onclickmethods()
    {
        btnpin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setpin();

            }
        });

        txtcreatepin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callcreatepin();
            }
        });
    }

    private void callcreatepin()
    {
        startActivity(new Intent(SetPin.this,CreatePin.class));
    }

    private void logout()
    {
        userSessionManager.setLoggedin(false);
        finish();
        startActivity(new Intent(SetPin.this, LoginActivity.class));

    }

    private void setpin()
    {

        if(pin.equals(""))
        {
            pinview1.setError("Please enter 4 digit pin");
        }
        else
        {
            startActivity(new Intent(SetPin.this, InstructionActivity.class));
        }
    }
}
