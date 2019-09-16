package gamsystech.user.newbleupdated.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gamsystech.user.newbleupdated.R;

import gamsystech.user.newbleupdated.activities.readingdemo_activity.TreatmentGetResponseModel;

public class TreatmentGetAdapter extends RecyclerView.Adapter<TreatmentGetAdapter.ViewHolder>{

    List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails;
    Context context;

    /*constructor for recycle  view*/

    public TreatmentGetAdapter(Context context, List<TreatmentGetResponseModel.TreatmentDetail> treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.template_treatment_log_item,parent,false);
        return new TreatmentGetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TreatmentGetResponseModel.TreatmentDetail treatmentGetResponseModel = treatmentDetails.get(position);
        viewHolder.tv_right_hand_sys.setText(""+treatmentGetResponseModel.getSYSBeforeRight());
        viewHolder.tv_right_hand_dia.setText(""+treatmentGetResponseModel.getDIABeforeRight());
        viewHolder.tv_right_hand_pulse.setText(""+treatmentGetResponseModel.getPulseBeforeRight());

        viewHolder.tv_left_hand_sys.setText(""+treatmentGetResponseModel.getSYSBeforeLeft());
        viewHolder.tv_left_hand_dia.setText(""+treatmentGetResponseModel.getDIABeforeLeft());
        viewHolder.tv_left_hand_pulse.setText(""+treatmentGetResponseModel.getPulseBeforeLeft());
    }

    @Override
    public int getItemCount() {
        return treatmentDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        View view;

        TextView tv_right_hand_dia,tv_right_hand_sys,tv_right_hand_pulse;
        TextView tv_left_hand_dia,tv_left_hand_sys,tv_left_hand_pulse;
        TextView tv_start_time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            /*initilize parameter*/
            tv_start_time = (TextView) itemView.findViewById(R.id.tv_start_time);

            tv_right_hand_dia = (TextView) itemView.findViewById(R.id.tv_right_hand_dia);
            tv_right_hand_sys = (TextView) itemView.findViewById(R.id.tv_right_hand_sys);
            tv_right_hand_pulse = (TextView) itemView.findViewById(R.id.tv_right_hand_pulse);


            tv_left_hand_dia = (TextView) itemView.findViewById(R.id.tv_left_hand_dia);
            tv_left_hand_sys = (TextView) itemView.findViewById(R.id.tv_left_hand_sys);
            tv_left_hand_pulse = (TextView) itemView.findViewById(R.id.tv_left_hand_pulse);
        }
    }



}
