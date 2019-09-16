package gamsystech.user.newbleupdated.fragments.dashboard_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import gamsystech.user.newbleupdated.DatabaseHelper.DbHandler;
import gamsystech.user.newbleupdated.DatabaseHelper.SharedPreferenceService;
import gamsystech.user.newbleupdated.DatabaseHelper.localstorage.SharedPreferenceManager;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.instruction_activity.InstructionActivity;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.ReadingDemo;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.pojo.Log;
import gamsystech.user.newbleupdated.utils.AndyUtils;
import gamsystech.user.newbleupdated.utils.AppUtil;
import gamsystech.user.newbleupdated.utils.Constants;

public class DashBoardFragment extends Fragment implements DashBoardFragmentView.View {


    private DashBoardFragmentPresenter dashBoardFragmentPresenter;

    TextView txtIBLintialright, txtsysintialright, txtdiaintialright, txtpulseintialright;
    TextView txtinitialrright;
    TextView txtinitialrleft;
    TextView txtsyseintialleft, txtdiaeintialleft, txtpulseeintialleft;
    TextView textViewLeft_cycle1, textViewLeft_cycle2;
    TextView textViewRight_cycle1, textViewRight_cycle2;

    TextView txtinitialreading, txtfinalreading;


    TextView txtsysfinalright, txtdiafinalright, txtmpfinalright;
    TextView txtsysfinalleft, txtdiafinalleft, txtmpfinalleft;


    //cycle for left and right hand
    public final int RIGHT_HAND_CYCLE_1 = 1;
    public final int RIGHT_HAND_CYCLE_2 = 2;
    public final int LEFT_HAND_CYCLE_1 = 3;
    public final int LEFT_HAND_CYCLE_2 = 4;

    public String currentdate = " ", LHDN, RHDN;

    public int currentCycle = RIGHT_HAND_CYCLE_1;

    private CountDownTimer mCountDownTimer;

    private final int CYCLE_DURATION = 1 * 60 * 1000;

    private DashboardFragmentEvents dashboardFragmentEvents;
    private SharedPreferenceService dashboardpref;
    DbHandler dashbarddbhandler;

    String L_sys, L_dia, L_hr;

    //constructor
    public DashBoardFragment() {
        // Required empty public constructor
    }

    //fragment position instance
    public static Fragment newInstance(int position) {
        return new DashBoardFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmentdashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashboardFragmentEvents = (DashboardFragmentEvents) getActivity();
        dashboardpref = new SharedPreferenceService(getContext());
        dashbarddbhandler = new DbHandler(getContext());

        dashBoardFragmentPresenter = new DashBoardFragmentPresenter(getContext(), this);
        initview(view);

    }

    //initilization of view
    private void initview(View view) {
        textViewLeft_cycle1 = view.findViewById(R.id.textview_cycle1_left);
        textViewLeft_cycle2 = view.findViewById(R.id.textview_cycle2_left);
        textViewRight_cycle1 = view.findViewById(R.id.textview_cycle1_right);
        textViewRight_cycle2 = view.findViewById(R.id.textview_cycle2_right);

        //intilial reading right hand device
        txtsysintialright = view.findViewById(R.id.txtsysintialright);
        txtdiaintialright = view.findViewById(R.id.txtdiaintialright);
        txtpulseintialright = view.findViewById(R.id.txtpulseintialright);
        txtIBLintialright = view.findViewById(R.id.txtIBLintialright);

        //initial reading left hand device
        txtsyseintialleft = view.findViewById(R.id.txtsyseintialleft);
        txtdiaeintialleft = view.findViewById(R.id.txtdiaeintialleft);
        txtpulseeintialleft = view.findViewById(R.id.txtpulseeintialleft);



        /*final  BP reading right device*/
        txtsysfinalright = view.findViewById(R.id.txtsysfinalright);
        txtdiafinalright = view.findViewById(R.id.txtdiafinalright);
        txtmpfinalright = view.findViewById(R.id.txtmpfinalright);

        /*final  BP reading left device*/
        txtsysfinalleft = view.findViewById(R.id.txtsysfinalleft);
        txtdiafinalright = view.findViewById(R.id.txtdiafinalright);
        txtmpfinalright = view.findViewById(R.id.txtmpfinalright);


        //intial right and left device name
        txtinitialrright = view.findViewById(R.id.txtinitialrright);
        txtdiafinalleft = view.findViewById(R.id.txtdiafinalleft);
        txtmpfinalleft = view.findViewById(R.id.txtmpfinalleft);


        txtinitialreading = view.findViewById(R.id.txtinitialreading);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater optioninflater) {
        optioninflater.inflate(R.menu.setting_menu, menu);
        super.onCreateOptionsMenu(menu, optioninflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;

        switch (item.getItemId()) {
            case R.id.setting:
                i = new Intent(getContext(), InstructionActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Display BP readings based on current cycle
     * in right hand or left hand
     * */
    public void displayBPReadings(String IBL, String sys, String dia, String HR) {


        /*shared preference value*/
        currentdate = AndyUtils.getcurrentDateTime();

        LHDN = dashboardpref.getPreference(getContext(), "", "LHDN");
        RHDN = dashboardpref.getPreference(getContext(), "", "RHDN");


        try {

            if (currentCycle == RIGHT_HAND_CYCLE_1) {


                txtinitialrright.setText("" + RHDN);
                txtinitialreading.setText("" + currentdate);
                txtIBLintialright.setText("IBL = " + IBL);                     //initlial battery level
                txtsysintialright.setText("Sys =" + "" + sys);                  //MAP intilial righ hand
                txtdiaintialright.setText("Dia =" + "" + dia);                  //Systolic initial righ hand
                txtpulseintialright.setText("Pulse =" + "" + HR);              //Pulse initlial righ hand


            }
            if (currentCycle == LEFT_HAND_CYCLE_1) {

                txtinitialrright.setText("" + LHDN);
                txtsyseintialleft.setText("Sys =" + sys);
                txtdiaeintialleft.setText("Dia=" + dia);
                txtpulseeintialleft.setText("PUlse=" + HR);



            }

            if (currentCycle == RIGHT_HAND_CYCLE_2) {
                txtsysfinalright.setText("Sys = " + sys);
                txtdiafinalright.setText("Dia = " + dia);
                txtmpfinalright.setText("Pulse = " + HR);


            }
            if (currentCycle == LEFT_HAND_CYCLE_2) {
                txtsysfinalleft.setText("Sys = " + sys);
                txtdiafinalleft.setText("Dia = " + dia);
                txtpulseeintialleft.setText("Pulse = " + HR);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removepreferences() {
        dashboardpref.removePreference(getContext(), "PREFS_NAME", "IBL");
        dashboardpref.removePreference(getContext(), "PREFS_NAME", "SYS");
        dashboardpref.removePreference(getContext(), "PREFS_NAME", "DIA");
        dashboardpref.removePreference(getContext(), "PREFS_NAME", "HR");

    }

    /*countdown for specific minite*/

    public void startORResumeTreatment() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(CYCLE_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;          //CONVERT TO SECOUND
                long minutes = seconds / 60;                        //CONVERT TO MIN

                if (minutes > 0)                            //if we have minutes, then there might be some remainder seconds
                    seconds = seconds % 60;             //seconds can be between 0-60, so we use the % operator to get the remainder

                //similar to seconds
                String time = formatNumber(minutes) + ":" + formatNumber(seconds);

                if (!textViewLeft_cycle1.getText().toString().contains("Complete")) {
                    textViewLeft_cycle1.setTypeface(textViewLeft_cycle1.getTypeface(), Typeface.BOLD);
                    textViewLeft_cycle1.setText("Cycle 1 RHD(5m): " + time);

                    //connect to left hand device and start the device
                    dashboardFragmentEvents.onCycleOneDeviceOneFinished();


                } else if (!textViewRight_cycle1.getText().toString().contains("Complete")) {
                    textViewRight_cycle1.setTypeface(textViewRight_cycle1.getTypeface(), Typeface.BOLD);
                    textViewRight_cycle1.setText("Cycle 1 LHD(5m): " + time);


                    //connect to righ hand device again and start device
                    //  dashboardFragmentEvents.onCycleTwoDeviceTwoFinshed();

                } else if (!textViewLeft_cycle2.getText().toString().contains("Complete")) {
                    textViewLeft_cycle2.setTypeface(textViewLeft_cycle2.getTypeface(), Typeface.BOLD);
                    textViewLeft_cycle2.setText("Cycle 2 (5m): " + time);

                    // currentCycle= LEFT_HAND_CYCLE_2;
                } else {
                    textViewRight_cycle2.setTypeface(textViewRight_cycle2.getTypeface(), Typeface.BOLD);
                    textViewRight_cycle2.setText("Cycle 2 (5m): " + time);

                }

            }


            @Override
            public void onFinish() {
                if (!textViewLeft_cycle1.getText().toString().contains("Complete")) {
                    textViewLeft_cycle1.setText("Cycle 1 (5m): Complete");

                    //current cycle is left hand cycle
                    currentCycle = LEFT_HAND_CYCLE_1;


                } else if (!textViewRight_cycle1.getText().toString().contains("Complete")) {
                    textViewLeft_cycle2.setTypeface(Typeface.SERIF, Typeface.NORMAL);
                    textViewLeft_cycle1.setText("Cycle 1 (5m): COMPLETE");

                    savetwobprecords();         //save records of two BP
                    currentCycle = RIGHT_HAND_CYCLE_2;           //current cycle is left hand cycle 1
                    //startORResumeTreatment();

                } else if (!textViewLeft_cycle2.getText().toString().contains("Complete")) {
                    textViewLeft_cycle2.setTypeface(Typeface.SERIF, Typeface.NORMAL);
                    textViewLeft_cycle2.setText("Cycle 2 (5m): Complete");

                    currentCycle = LEFT_HAND_CYCLE_2;           //current cycle is left hand cycle 1
                   // startORResumeTreatment();

                    /*connect to right hand device*/
                    // dashboardFragmentEvents.onCycleTwoDeviceTwoFinshed();
                    currentCycle = RIGHT_HAND_CYCLE_2;           //current cycle is left hand cycle 1


                } else {
                    textViewRight_cycle2.setText("Cycle 2 (5m): Complete");
                    textViewRight_cycle2.setTypeface(Typeface.SERIF, Typeface.NORMAL);

                    showTreatmentCompleteDialog();
                }

            }


        }.start();
    }

    /*save data to sqllite*/
    public void savetwobprecords() {

        try {
            String start_time = AndyUtils.getcurrentDate();
            android.util.Log.d("abhi",start_time);

            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            int r_sys = Integer.parseInt(txtsysintialright.getText().toString().replaceAll("[^0-9]", ""));
            int r_dia = Integer.parseInt(txtdiaintialright.getText().toString().replaceAll("[^0-9]", ""));
            int r_pulse = Integer.parseInt(txtpulseintialright.getText().toString().replaceAll("[^0-9]", ""));

            int l_sys = Integer.parseInt(txtsyseintialleft.getText().toString().replaceAll("[^0-9]", ""));
            int l_dia = Integer.parseInt(txtdiaeintialleft.getText().toString().replaceAll("[^0-9]", ""));
            int l_pulse = Integer.parseInt(txtpulseeintialleft.getText().toString().replaceAll("[^0-9]", ""));
            int userid = Integer.parseInt(SharedPreferenceManager.getUserId());

            TreatmentRequestModel treatmentRequestModel = new TreatmentRequestModel();


            treatmentRequestModel.setUserId(userid);
            treatmentRequestModel.setSYSBeforeRight(r_sys);
            treatmentRequestModel.setDIABeforeRight(r_dia);
            treatmentRequestModel.setPulseBeforeRight(r_pulse);


            treatmentRequestModel.setSYSBeforeLeft(l_sys);
            treatmentRequestModel.setDIABeforeLeft(l_dia);
            treatmentRequestModel.setPulseBeforeLeft(l_pulse);

            treatmentRequestModel.setSYSAfterRight(123);
            treatmentRequestModel.setDIAAfterRight(123);
            treatmentRequestModel.setPulseAfterRight(123);

            treatmentRequestModel.setSYSAfterLeft(123);
            treatmentRequestModel.setDIAAfterLeft(123);
            treatmentRequestModel.setPulseAfterLeft(123);
            treatmentRequestModel.setTreatmentDate(start_time);


            android.util.Log.d("tag", "" + treatmentRequestModel);


            // pass data to summary fragment
            launchSummaryFragment(treatmentRequestModel);

            /*save to server*/
            dashBoardFragmentPresenter.OnBpTreatmentComplete(treatmentRequestModel);

            /*save to local storage*/
            String mob = dashboardpref.getPreference(getContext(), "", "mobileno");
            savetosqllite(start_time, r_sys, r_dia, r_pulse, l_sys, l_dia, l_pulse, mob);


        } catch (Exception e) {
            e.getMessage();
        }
    }

    /*save to sqllite*/
    private void savetosqllite(String start_time, int r_sys, int r_dia, int r_pulse, int l_sys, int l_dia, int l_pulse, String mob) {
        /*      for local storage sqllite    */
        Log treatment_logs = new Log();
        treatment_logs.setRightHandSystolic(r_sys);
        treatment_logs.setRightHandDiastolic(r_dia);
        treatment_logs.setRightHandPulse(r_pulse);

        treatment_logs.setLeftHandSystolic(l_sys);
        treatment_logs.setLeftHandDiastolic(l_dia);
        treatment_logs.setLeftHandPulse(l_pulse);
        treatment_logs.setTreatmentStartTimeStamp(start_time);
        DbHandler db = new DbHandler(getContext().getApplicationContext());
        DbHandler.getInstance(getActivity()).addTreatment(start_time, r_sys, r_dia, r_pulse, l_sys, l_dia, l_pulse, mob);
        db.close();
        Toast.makeText(getContext().getApplicationContext(), "Item Added to local sqlite" + treatment_logs, Toast.LENGTH_SHORT).show();

    }


    public void resetCycleValue() {
        if (!textViewLeft_cycle1.getText().toString().contains("Complete")) {
            textViewLeft_cycle1.setTypeface(textViewLeft_cycle1.getTypeface(), Typeface.BOLD);
            textViewLeft_cycle1.setText("Cycle 1 (5m): " + 0);
        } else if (!textViewRight_cycle1.getText().toString().contains("Complete")) {
            textViewRight_cycle1.setTypeface(textViewRight_cycle1.getTypeface(), Typeface.BOLD);
            textViewRight_cycle1.setText("Cycle 1 (5m): " + 0);
        } else if (!textViewLeft_cycle2.getText().toString().contains("Complete")) {
            textViewLeft_cycle2.setTypeface(textViewLeft_cycle2.getTypeface(), Typeface.BOLD);
            textViewLeft_cycle2.setText("Cycle 2 (5m): " + 0);
        } else {
            textViewRight_cycle2.setTypeface(textViewRight_cycle2.getTypeface(), Typeface.BOLD);
            textViewRight_cycle2.setText("Cycle 2 (5m): " + 0);
        }
    }


    /**
     * Stop timer
     */
    public void stopTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private void showTreatmentCompleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Treatment Complete");
        builder.setMessage("Your Redoxer treatment done successfully!. \nSee you next time");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // saveNameToServer();
                dialogInterface.dismiss();
            }
        }).show();
    }


    private String formatNumber(long value) {
        if (value < 10)
            return "0" + value;
        return value + "";
    }

    /*on api call sucess */
    @Override
    public void OnFailed(String error) {
        AppUtil.showToast(getContext(), error);

    }

    /*on api call Failed */
    @Override
    public void Onsucess(TreatmentRequestModel treatmentRequestModel) {

        AndyUtils.showToastMsg(getContext(),"Treatment inserted sucessfully");
    }

    public void launchSummaryFragment(TreatmentRequestModel treatmentRequestModel) {
       /* SummaryFragment1 summaryFragment1 = new SummaryFragment1();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle b = new Bundle();
        b.putSerializable(Constants.KEY_TREATMENT_MODEL, treatmentRequestModel);
        summaryFragment1.setArguments(b);

        fragmentTransaction.replace(R.id.dashboard_viewpager, summaryFragment1);
        fragmentTransaction.commit();*/


//        Intent intent = new Intent(getActivity(), ReadingDemo.class);
//        intent.putExtra(Constants.KEY_TREATMENT_MODEL, treatmentRequestModel);
//       // intent.putExtra(Constants.KEY_TREATMENT_MODEL, b);
//        getActivity().startActivity(intent);
    }


    public interface DashboardFragmentEvents {
        void onCycleOneDeviceOneFinished();             //connect to left hand device once cycle one finished

        void onCycleTwoDeviceTwoFinshed();              //connect to right hand device again
    }

}
