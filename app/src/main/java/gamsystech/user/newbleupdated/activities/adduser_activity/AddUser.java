package gamsystech.user.newbleupdated.activities.adduser_activity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gamsystech.user.newbleupdated.DatabaseHelper.DbHandler;
import gamsystech.user.newbleupdated.DatabaseHelper.localstorage.SharedPreferenceManager;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.utils.AndyUtils;
import gamsystech.user.newbleupdated.utils.AppUtil;


public class AddUser extends AppCompatActivity implements AddUserActivityView.View {


    private static final String TAG = "adduser";
    @BindView(R.id.btnad)
    public Button btnad;

    @BindView(R.id.et_reg_pass)
    public EditText et_reg_pass;

    @BindView(R.id.et_reg_mob)
    public EditText et_reg_mob;

    @BindView(R.id.et_reg_repass)
    public EditText et_reg_repass;


    @BindView(R.id.adutoolbar)
    Toolbar adutoolbar;


    DbHandler dbUser;
    Resources resources;
    private AddUserActivityPresenter addUserActivityPresenter;

    RegisterRequestModel registerRequestModel;

    @SuppressLint("HardwareIds")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user2);
        ButterKnife.bind(this);

        setSupportActionBar(adutoolbar);

        /*Initilization*/
        startadduserMVP();

        getIntentData();


    }

    private void startadduserMVP() {
        addUserActivityPresenter = new AddUserActivityPresenter(this, this);
    }


    public void getIntentData() {
        if (getIntent() != null) {
            registerRequestModel = (RegisterRequestModel) getIntent().getSerializableExtra("all_data");
        }
    }


    private boolean isValidated() {
        if (TextUtils.isEmpty(et_reg_mob.getText().toString().trim())) {
            et_reg_mob.setError(resources.getString(R.string.please_enter_mobile_number));
            Toast.makeText(this, resources.getString(R.string.please_enter_mobile_number), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_reg_pass.getText().toString().trim())) {
            Toast.makeText(this, resources.getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!et_reg_repass.equals(et_reg_pass)) {
            AndyUtils.showToastMsg(this, "Please Reenter same password");
            et_reg_repass.setError("Please Reenter same password");
            return false;
        }

        return true;
    }




    @OnClick(R.id.btnad)
    public void OnAddUserSucess() {


        if (isValidated()) {
            addUserActivityPresenter.onRegister(getUserData());
            et_reg_mob.setEnabled(false);
            et_reg_pass.setEnabled(false);
        }

    }

    private RegisterRequestModel getUserData() {
        registerRequestModel.setMobileNumber(et_reg_mob.getText().toString().trim());
        registerRequestModel.setPin(et_reg_pass.getText().toString().trim());

        adduserdatabase(registerRequestModel);             //add to local database
        return registerRequestModel;
    }

    //save using sqllite
    private void adduserdatabase(RegisterRequestModel registerRequestModel) {

        SharedPreferenceManager.saveRegisterRequestModel(registerRequestModel);             //store all data in hock
        AndyUtils.showToastMsg(this, "user added sucessfully" + registerRequestModel);
        finish();
    }
    @Override
    public void OnFailed(String error) {
        AppUtil.showToast(this, error);
        et_reg_mob.setEnabled(true);
        et_reg_pass.setEnabled(true);

    }

    @Override
    public void getStateListFromServer(String countryId) {

    }

    @Override
    public void getCityListFromServer(Integer stateId) {

    }

}
