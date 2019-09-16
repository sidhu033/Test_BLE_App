package gamsystech.user.newbleupdated.activities.registration_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.adduser_activity.AddUser;
import gamsystech.user.newbleupdated.utils.AppUtil;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityView.View {

    @BindView(R.id.btn_reg_user)
    public Button btn_reg_user;

    @BindView(R.id.et_reg_username)
    public EditText et_reg_username;

    @BindView(R.id.et_reg_email)
    EditText et_reg_email;


    @BindView(R.id.et_reg_name)
    EditText et_reg_name;

    @BindView(R.id.et_reg_lastname)
    EditText et_reg_lastname;


    @BindView(R.id.tv_reg_dob)
    TextView tv_reg_dob;


    @BindView(R.id.cb_agreetandcondition)
    CheckBox cb_agreetandcondition;


    @BindView(R.id.rb_male)
    RadioButton rb_male;

    @BindView(R.id.rb_female)
    RadioButton rb_female;

    @BindView(R.id.rg_reg_gender)
    RadioGroup rg_reg_gender;


    @BindView(R.id.tv_reg_state)
    TextView tv_reg_state;

    @BindView(R.id.tv_reg_city)
    TextView tv_reg_city;

    @BindView(R.id.tv_reg_address)
    TextView tv_reg_address;

    @BindView(R.id.tv_reg_area)
    TextView tv_reg_area;

    @BindView(R.id.et_reg_pincode)
    TextView et_reg_pincode;



    private static final String TAG = "RegisterActivity";
    private RegisterActivityPresenter mRegisterActivityPresenter;
    Context context;
    Calendar cal = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dobdateSetListener;


    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);

    Resources resources;
    String email, f_name, last_name, username, address, area,pincode;
    String date;
    private String dob, gender;
    RegisterActivityPresenter registerActivityPresenter;
    private Dialog dialog;
    private Integer stateId;
    private Integer cityId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        /*initilize*/
        initialize();
        startmvp();
    }

    private void startmvp() {
        registerActivityPresenter = new RegisterActivityPresenter(this, this);
    }

    /*initlize resources*/
    private void initialize() {
        context = this;
        resources = getResources();

        /*string initlize*/
        email = et_reg_email.getText().toString();
        f_name = et_reg_name.getText().toString();
        last_name = et_reg_lastname.getText().toString();
        username = et_reg_username.getText().toString();
        address = tv_reg_address.getText().toString();
        area = tv_reg_area.getText().toString();
        pincode = et_reg_pincode.getText().toString();

        dobdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = day + "/" + month + "/" + year;
                tv_reg_dob.setText(date);
            }
        };

        tv_reg_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, dobdateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

    }


    @OnClick(R.id.tv_reg_state)
    public void selectstatelist() {
        registerActivityPresenter.getStateListFromServer(101);

    }

    @OnClick(R.id.rb_male)
    public void maleGenderClick(){


        rb_male.setChecked(true);
        rb_female.setChecked(false);
        gender = "male";
    }

    @OnClick(R.id.rb_female)
    public void femaleGenderClick(){
        rb_male.setChecked(false);
        rb_female.setChecked(true);
        gender = "female";
    }
    @OnClick(R.id.tv_reg_city)
    public void selectcitylist() {
        registerActivityPresenter.getCityListFromServer(stateId);
    }

    @OnClick(R.id.btn_reg_user)
    public void registeUser() {

        if (isValidated()) {
            RegisterRequestModel registerRequestModel = new RegisterRequestModel();
            registerRequestModel.setUserName(username);
            registerRequestModel.setFirstName(f_name);
            registerRequestModel.setLastName(last_name);
            registerRequestModel.setEmailId(email);
            registerRequestModel.setDateOfBirth(tv_reg_dob.getText().toString());
            registerRequestModel.setGender(gender);
            registerRequestModel.setArea(area);
            registerRequestModel.setCountryId(101);
            registerRequestModel.setStateId(stateId);
            registerRequestModel.setCityId(cityId);
            registerRequestModel.setPinCode(pincode);
            registerRequestModel.setDeviceId(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

            Intent i = new Intent(context, AddUser.class);
            i.putExtra("all_data", (Serializable) registerRequestModel);
            startActivity(i);
        }
    }


    /*on create closed*/

    /**
     * validation here
     *
     * @return
     */
    private boolean isValidated() {

        if (TextUtils.isEmpty(et_reg_name.getText().toString().trim())) {
            Toast.makeText(this, resources.getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            et_reg_name.setError(resources.getString(R.string.please_enter_name));
            return false;
        }
        if (TextUtils.isEmpty(et_reg_lastname.getText().toString().trim())) {

        }
        if (TextUtils.isEmpty(et_reg_email.getText().toString().trim())) {
            et_reg_email.setError(resources.getString(R.string.please_enter_email));
            Toast.makeText(this, resources.getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cb_agreetandcondition.isChecked()) {
            Toast.makeText(this, resources.getString(R.string.checkbox_validate), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!emailValidator(et_reg_email.getText().toString().trim())) {
            et_reg_email.setError("Enter in valid format");
            return false;
        }

        return true;
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void OnFailed(String error) {
        AppUtil.showToast(this, error);

    }

    @Override
    public void onStateListSuccess(List<CountryListResponseModel.StatesList> statesList) {

        dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.custom_state_list_dailog);
        //   dialog.setTitle(R.string.dialog_title);

        RecyclerView rvStateList = dialog.findViewById(R.id.rvStateList);

        StateListAdapter treatmentGetAdapter = new StateListAdapter(this, statesList, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvStateList.setLayoutManager(layoutManager);
        rvStateList.setItemAnimator(new DefaultItemAnimator());
        rvStateList.setAdapter(treatmentGetAdapter);

        dialog.show();
    }



    @Override
    public void setStateId(Integer stateId, String stateName, boolean isFromState) {
        if (dialog != null) {
            dialog.dismiss();

            if (isFromState) {
                this.stateId = stateId;
                tv_reg_state.setText(stateName);
            } else {
                this.cityId = stateId;
                tv_reg_city.setText(stateName);
            }
        }
    }

    List<CityListResponseModel.CitiesList> citiesList = new ArrayList<>();

    @Override
    public void onCityListSucess(List<CountryListResponseModel.CitiesList> citiesList) {
        dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.custom_state_list_dailog);
        //   dialog.setTitle(R.string.dialog_title);

        RecyclerView rvStateList = dialog.findViewById(R.id.rvStateList);

        StateListAdapter treatmentGetAdapter = new StateListAdapter(this, false, citiesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvStateList.setLayoutManager(layoutManager);
        rvStateList.setItemAnimator(new DefaultItemAnimator());
        rvStateList.setAdapter(treatmentGetAdapter);

        dialog.show();
    }
}
