package gamsystech.user.newbleupdated.activities.readingdemo_activity;

import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gamsystech.user.newbleupdated.DatabaseHelper.localstorage.SharedPreferenceManager;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.home_activity.HomeActivity;
import gamsystech.user.newbleupdated.adapters.TreatmentGetAdapter;
import gamsystech.user.newbleupdated.fragments.dashboard_fragment.DashBoardFragment;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.services.BluetoothLeService;
import gamsystech.user.newbleupdated.utils.AppUtil;

import static android.support.constraint.Constraints.TAG;

public class ReadingDemo extends AppCompatActivity implements ReadingDemoView.View {


    @BindView(R.id.readingdemorecycle)
    RecyclerView readingdemorecycle;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvmonthyear)
    TextView tvmonthyear;
    Context context;


    TreatmentRequestModel treatmentRequestModel = new TreatmentRequestModel();
    ReadingDemoPresenter readingDemoPresenter;

    List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails = new ArrayList<>();
    TreatmentGetAdapter treatmentGetAdapter;

    int userid = Integer.parseInt(SharedPreferenceManager.getUserId());
    Calendar cal = Calendar.getInstance();
    Integer year = cal.get(Calendar.YEAR);
    Integer month = cal.get(Calendar.MONTH);
    private DatePickerDialog.OnDateSetListener listener;
    DashBoardFragment mDashboardFragament;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_demo);


        ButterKnife.bind(this);
        initilizerecycleview();
        startMVPReadingDemo();


        // treatmentRequestModel = (TreatmentRequestModel) getIntent().getSerializableExtra(Constants.KEY_TREATMENT_MODEL);
         updateUI();


    }

    //get data from server
    private void startMVPReadingDemo() {
        readingDemoPresenter = new ReadingDemoPresenter(this, this);
    }

    private void initilizerecycleview() {
        context = this;
        TreatmentGetAdapter treatmentGetAdapter = new TreatmentGetAdapter(this, treatmentDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        readingdemorecycle.setLayoutManager(layoutManager);
        readingdemorecycle.setItemAnimator(new DefaultItemAnimator());
        readingdemorecycle.setAdapter(treatmentGetAdapter);
    }


    @OnClick(R.id.tvmonthyear)
    public void openmonthyeardailog() {

       /* MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(listener);
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");*/
        showDatePicker();
    }

    /**
     * display date picker
     */
    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {

                //  month = monthOfYear;
                //   year = year1;
                Log.d(TAG, "onDateSet: ---" + monthOfYear + "---" + year1);

                readingDemoPresenter.getTreatmentData(adddata(monthOfYear, year1));
               /* tvDate.setText(Common.getDateFormat(year, (monthOfYear), dayOfMonth));
                invitationDate = (Common.getDateFormat(year, (monthOfYear), dayOfMonth));*/
                Log.d(TAG, "onDateSet: ---" + monthOfYear + "---" + year1);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

      /*  datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                liDobSelector.setClickable(true);
            }
        });*/

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }


    public TreatmentGetRequestModel adddata(int monthOfYear, int year1) {
        TreatmentGetRequestModel treatmentGetRequestModel = new TreatmentGetRequestModel();
        treatmentGetRequestModel.setUserId(userid);
        treatmentGetRequestModel.setMonth(monthOfYear);
        treatmentGetRequestModel.setYear(year1);
        return treatmentGetRequestModel;
    }


    private void updateUI() {
        btnLogin.setText("REstart Device");


        if (treatmentRequestModel != null) {
        /*    tv_right_hand_sys.setText("" + treatmentRequestModel.getSYSBeforeRight());
            tv_right_hand_dia.setText("" + treatmentRequestModel.getDIABeforeRight());
            tv_right_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeRight());
            tv_start_time.setText(AndyUtils.getcurrentDateTime());
            tv_left_hand_sys.setText("" + treatmentRequestModel.getSYSBeforeLeft());
            tv_left_hand_dia.setText("" + treatmentRequestModel.getDIABeforeLeft());
            tv_left_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeLeft());*/
        }
    }

    @OnClick(R.id.btnLogin)
    public void restartdeviceagain()
    {
        RestartDevice("restart device");
    }

    private void RestartDevice(String errormessgae) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RESTART YOUR DEVICE");

        builder.setMessage(errormessgae);
        builder.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {




            }
        });
    }


    /*on failed of api hit call*/
    @Override
    public void OnFailed(String error) {
        AppUtil.showToast(this, error);
    }

    @Override
    public void onSuccess(List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails) {

        setAdapter(treatmentDetails);
    }


    private void setAdapter(List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails) {
        setLayoutManager();
        TreatmentGetAdapter treatmentGetAdapter = new TreatmentGetAdapter(context, treatmentDetails);
        readingdemorecycle.setAdapter(treatmentGetAdapter);
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        readingdemorecycle.setLayoutManager(layoutManager);
        readingdemorecycle.setItemAnimator(new DefaultItemAnimator());
    }

   /* private void getDataUsingIntent() {

        if (getIntent()!=null)
        {


            Log.d(TAG, "onCreate: ---" + treatmentRequestModel);
            SYSBeforeRight =getIntent().getIntExtra("SYSBeforeRight",0);
            DIABeforeRight =  getIntent().getIntExtra("DIABeforeRight",0);
            PULSEBeforeRight =  getIntent().getIntExtra("PULSEBeforeRight",0);

            SYSBeforeLeft =  getIntent().getIntExtra("SYSBeforeLeft",0);
            DIABeforeLeft =  getIntent().getIntExtra("DIABeforeLeft",0);
            PULSEBeforeLeft =  getIntent().getIntExtra("PULSEBeforeLeft",0);
            //  Log.d("oncreate readio", String.valueOf(SYSBeforeRight));

            tv_right_hand_sys.setText(""+SYSBeforeRight);
            tv_right_hand_dia.setText(""+DIABeforeRight);
            tv_right_hand_pulse.setText(""+PULSEBeforeRight);

            tv_left_hand_sys.setText(""+SYSBeforeLeft);
            tv_left_hand_dia.setText(""+DIABeforeLeft);
            tv_left_hand_pulse.setText(""+PULSEBeforeLeft);

        }
    }*/


}
