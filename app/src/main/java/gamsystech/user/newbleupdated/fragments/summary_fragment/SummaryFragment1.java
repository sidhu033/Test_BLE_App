package gamsystech.user.newbleupdated.fragments.summary_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.utils.AndyUtils;
import gamsystech.user.newbleupdated.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SummaryFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SummaryFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TreatmentRequestModel treatmentRequestModel;

    TextView tv_right_hand_dia, tv_right_hand_sys, tv_right_hand_pulse;
    TextView tv_left_hand_dia, tv_left_hand_sys, tv_left_hand_pulse;
    TextView tv_start_time;

    public SummaryFragment1() {
        // Required empty public constructor
    }
    /*create new instance of summary fragment*/
    public SummaryFragment1 newInstance()
    {
        return new SummaryFragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            treatmentRequestModel = (TreatmentRequestModel) getArguments().getSerializable(Constants.KEY_TREATMENT_MODEL);
          //  Log.d(TAG, "onCreate: ---" + treatmentRequestModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        initilizeUI(view);
        updateUI();
        return view;
    }

    /**
     * setting UI of Treatment left and right hand initial readings on textview
     */
    private void updateUI() {

        if(treatmentRequestModel != null)
        {
            tv_right_hand_dia.setText("" + treatmentRequestModel.getSYSBeforeRight());
            tv_right_hand_sys.setText("" + treatmentRequestModel.getDIABeforeRight());
            tv_right_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeRight());
            tv_start_time.setText(AndyUtils.getcurrentDateTime());
            tv_left_hand_dia.setText("" + treatmentRequestModel.getDIABeforeLeft());
            tv_left_hand_sys.setText("" + treatmentRequestModel.getSYSBeforeLeft());
            tv_left_hand_pulse.setText("" + treatmentRequestModel.getPulseBeforeLeft());
          //  Log.d(TAG,"debug value"+treatmentRequestModel.getTreatmentDate());
        }
    }

    /**
     * initilize UI Here
     *
     * @param rootView
     */
    private void initilizeUI(View rootView) {
        tv_start_time = (TextView) rootView.findViewById(R.id.tv_start_time);
        tv_right_hand_dia = (TextView) rootView.findViewById(R.id.tv_right_hand_dia);
        tv_right_hand_sys = (TextView) rootView.findViewById(R.id.tv_right_hand_sys);
        tv_right_hand_pulse = (TextView) rootView.findViewById(R.id.tv_right_hand_pulse);
        tv_left_hand_dia = (TextView) rootView.findViewById(R.id.tv_left_hand_dia);
        tv_left_hand_sys = (TextView) rootView.findViewById(R.id.tv_left_hand_sys);
        tv_left_hand_pulse = (TextView) rootView.findViewById(R.id.tv_left_hand_pulse);

        Log.d("tag", String.valueOf(tv_right_hand_dia));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
