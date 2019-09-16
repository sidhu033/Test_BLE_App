package gamsystech.user.newbleupdated.activities.login_activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gamsystech.user.newbleupdated.DatabaseHelper.DbHandler;
import gamsystech.user.newbleupdated.DatabaseHelper.PreferenceHelper;
import gamsystech.user.newbleupdated.DatabaseHelper.SharedPreferenceService;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterActivity;
import gamsystech.user.newbleupdated.session.UserSessionManager;
import gamsystech.user.newbleupdated.utils.AndyUtils;


public class LoginActivity extends AppCompatActivity implements LoginActivityView.View, LoginActivityView {
    //initilization
    private String TAG = "LoginActivity";
    private final int LoginTask = 1;
    String username, password, mobilenumber, DeviceId;

    @BindView(R.id.etUsername)
    public EditText etUsername;

    @BindView(R.id.etPassword)
    public EditText etPassword;


    @BindView(R.id.btnLogin)
    public Button btnLogin;

    @BindView(R.id.tvNewUser)
    public TextView tvNewUser;

    @BindView(R.id.tvForgotPassword)
    public TextView tvForgotPassword;

    AndyUtils andyUtils;

    private UserSessionManager session;
    private PreferenceHelper preferenceHelper;
    private UserSessionManager userSessionManager;
    private DbHandler dbHandler;

    ProgressDialog progressDialog;
    SharedPreferenceService loginpref;
    LoginActivityPresenter loginActivityPresenter;
    Resources resources;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        resources = getResources();
        Initilization();
        startLoginMVP();
    }

    //intilize mvp
    private void startLoginMVP() {
        loginActivityPresenter = new LoginActivityPresenter(this, this, resources);

    }


    //intilization
    private void Initilization() {

        preferenceHelper = new PreferenceHelper(this);
        session = new UserSessionManager(getApplicationContext());
        andyUtils = new AndyUtils();
        progressDialog = new ProgressDialog(this);
        dbHandler = new DbHandler(this);
        userSessionManager = new UserSessionManager(this);
        loginpref = new SharedPreferenceService(this);


        //insttiation

      /* if(userSessionManager.loggedin())
        {
            startActivity(new Intent(LoginActivity.this,SetPin.class));
            finish();
        }*/
        // if(preferenceHelper.getIsLogin())
        //{
        // Intent intent= new Intent(LoginActivity.this , WelcomeActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        // startActivity(intent);
        // this.finish();
        //}


    }

    //on create closed
    @OnClick(R.id.btnLogin)
    public void login() {
        username = etUsername.getText().toString().trim();                  //mobile number
        password = etPassword.getText().toString().trim();                 //password of user
        mobilenumber = String.valueOf(loginpref.setPreference(getApplicationContext(), "mobileno", username));
       // IMEI = AndyUtils.findIMEI(this);      //imei number

        DeviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (isValidateLogin()) {

                loginActivityPresenter.onLogin(username,password,DeviceId);


            /*add to sqllite database*/
            //  dbHandler.adduser(username, password);
        }

    }

    @OnClick(R.id.tvNewUser)
    public void launchregistration()
    {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    /**
     * validation here if edit text is empty
     *
     * @return
     */
    private boolean isValidateLogin() {

        if (!AndyUtils.isNetworkAvailable(this)) {
            AndyUtils.showToastMsg(this, resources.getString(R.string.network_not_found));
            return false;
        }

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Enter Registered Mobile Number");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(resources.getString(R.string.please_enter_password));
            return false;
        }
        if (!AndyUtils.Number_Validate(username)) {
            etUsername.setError("Invalid Mobile number ");
            etUsername.requestFocus();
            return false;
        }
       /* if (!AndyUtils.Password_Validate(password)) {
            etpassword.setError("Password should be Upper case, special Character and number");
            etpassword.requestFocus();
            return false;
        }*/

       return  true;

    }


    //on back stag
    @Override
    public void onBackPressed() {
        // AndyUtils.showSimpleProgressDialog(this,"ok","ok",true);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        builder.setMessage("DO YOU WANT EXIT");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                LoginActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //on failed of particular api
    @Override
    public void OnFailed(String error) {
        AndyUtils.showToastMsg(this,error);
        etUsername.setFocusable(false);
        etPassword.setFocusable(false);
    }

    @Override
    public void OnSucess(LoginResponseModel loginResponseModel) {

         loginActivityPresenter.luanchInstructionactivity(loginResponseModel.getUserId());
    }

    //on sucess




}


