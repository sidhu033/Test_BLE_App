package gamsystech.user.newbleupdated.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gamsystech.user.newbleupdated.R;


public class CreatePin extends AppCompatActivity
{
    Button btncratepin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        Initilization();
        onclicklistner();
    }


    private void Initilization()
    {
        btncratepin = findViewById(R.id.btncratepin);

    }


    private void onclicklistner()
    {
        btncratepin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getBaseContext(),SetPin.class));
            }
        });
    }

}
