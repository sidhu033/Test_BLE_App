package gamsystech.user.newbleupdated.fragments.summary_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gamsystech.user.newbleupdated.DatabaseHelper.DbHandler;
import gamsystech.user.newbleupdated.DatabaseHelper.SharedPreferenceService;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.adapters.TreatmentLogAdapter;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.pojo.Log;
import gamsystech.user.newbleupdated.utils.Constants;


public class SummaryFragment extends Fragment implements SummaryFragmentView.View
{
    SummaryFragmentPresenter summaryFragmentPresenter;
    Context  summaryfragmentcontext;
    private static final String TAG = "SummaryFragment";




    private RecyclerView rvTreatmentLogs;
    private TextView tvMessage;

    /*for multipe treatment value*/
    private ArrayList<Log> mTreatmentLogs;

    /*for single treatment value*/
    TreatmentRequestModel treatmentRequestModel = new TreatmentRequestModel();


    SharedPreferenceService sharedPreferenceService = new SharedPreferenceService(getContext());
    String mobilenumberpref;


    TextView  tv_right_hand_dia,tv_right_hand_sys,tv_right_hand_pulse;
    TextView tv_left_hand_dia,tv_left_hand_sys,tv_left_hand_pulse;
    TextView tv_start_time;



    @SuppressLint("ValidFragment")
    public SummaryFragment()
    {
        this.summaryFragmentPresenter = summaryFragmentPresenter;
        this.summaryfragmentcontext = summaryfragmentcontext;
    }




    /*summary fragment instance*/
    public static Fragment newInstance(int position)
    {
        return new SummaryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            treatmentRequestModel = (TreatmentRequestModel) getArguments().getSerializable(Constants.KEY_TREATMENT_MODEL);
            android.util.Log.d(TAG, "onCreate: ---"+treatmentRequestModel);
        }
    }

    //initialization
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView= inflater.inflate(R.layout.message_content, container, false);

        //    tvMessage = rootView.findViewById(R.id.tv_logs_message);
        //     rvTreatmentLogs = rootView.findViewById(R.id.rv_logs);
        //rvTreatmentLogs.setLayoutManager(new LinearLayoutManager(getContext()));

        mobilenumberpref  =  sharedPreferenceService.getPreference(getContext(),"","mobileno");



        tv_start_time = (TextView) rootView.findViewById(R.id.tv_start_time);

        tv_right_hand_dia = (TextView) rootView.findViewById(R.id.tv_right_hand_dia);
        tv_right_hand_sys = (TextView) rootView.findViewById(R.id.tv_right_hand_sys);
        tv_right_hand_pulse = (TextView) rootView.findViewById(R.id.tv_right_hand_pulse);


        tv_left_hand_dia = (TextView) rootView.findViewById(R.id.tv_left_hand_dia);
        tv_left_hand_sys = (TextView) rootView.findViewById(R.id.tv_left_hand_sys);
        tv_left_hand_pulse = (TextView) rootView.findViewById(R.id.tv_left_hand_pulse);

        showrecordthroughmodel();

        /*show record through mobile number in sqllite*/
       // showtreatmentrecord(mobilenumberpref);


        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    private void showrecordthroughmodel()
    {

        // treatmentRequestModel=(TreatmentRequestModel) getArguments().getSerializable("treatmentRequestModel");
        //  android.util.Log.d("summary", String.valueOf(treatmentRequestModel));
        if(treatmentRequestModel != null)
        {
            tv_right_hand_dia.setText("" + treatmentRequestModel.getSYSBeforeRight());
            tv_right_hand_sys.setText("" + treatmentRequestModel.getDIABeforeRight());
            tv_right_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeRight());

            tv_left_hand_dia.setText("" + treatmentRequestModel.getDIABeforeLeft());
            tv_left_hand_sys.setText("" + treatmentRequestModel.getSYSBeforeLeft());
            tv_left_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeLeft());
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



    }
    private void showtreatmentrecord(String mobilenumberpref)
    {


     //   mTreatmentLogs= (ArrayList<Log>) DbHandler.getInstance(getContext()).getAllTreatment(mobilenumberpref);

        if (mTreatmentLogs.isEmpty())
        {
            tvMessage.setVisibility(View.VISIBLE);
            rvTreatmentLogs.setVisibility(View.GONE);
        }
        else
        {
            tvMessage.setVisibility(View.GONE);
            rvTreatmentLogs.setVisibility(View.VISIBLE);
            rvTreatmentLogs.setAdapter(new TreatmentLogAdapter(mTreatmentLogs,getActivity()));
        }
    }

    /*create new instance of summary fragment*/
    public SummaryFragment newInstance()
    {
        return new SummaryFragment();
    }

    /*on Api call failed*/
    @Override
    public void OnFailed(String error)
    {

    }
}
