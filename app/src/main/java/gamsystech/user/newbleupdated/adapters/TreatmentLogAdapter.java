package gamsystech.user.newbleupdated.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.util.List;

import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.pojo.Log;
import gamsystech.user.newbleupdated.utils.AndyUtils;

public class TreatmentLogAdapter extends RecyclerView.Adapter<TreatmentLogAdapter.ViewHolder>
{
    List<Log> treatmentLogAdapters;
    Context context;
   List<TreatmentRequestModel> treatmentRequestModels;

    public TreatmentLogAdapter(List<Log> treatmentLogAdapters, Context context)
   {        this.treatmentLogAdapters = treatmentLogAdapters;
       this.context = context;
   }

    /*public TreatmentLogAdapter(List<TreatmentRequestModel> treatmentRequestModels, Context context)
    {
        this.treatmentLogAdapters = treatmentLogAdapters;
        this.context = context;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.template_treatment_log_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
         Log log =    treatmentLogAdapters.get(position);
    //    String currentdate = AndyUtils.getcurrentDateTime();
        viewHolder.tv_right_hand_sys.setText(log.getRightHandSystolic());
        viewHolder.tv_right_hand_dia.setText(log.getRightHandDiastolic());
        viewHolder.tv_right_hand_pulse.setText(log.getRightHandPulse());

        viewHolder.tv_left_hand_sys.setText(log.getLeftHandSystolic());
        viewHolder.tv_left_hand_dia.setText(log.getLeftHandDiastolic());
        viewHolder.tv_left_hand_pulse.setText(log.getLeftHandPulse());
        viewHolder.tv_start_time.setText(log.getTreatmentStartTimeStamp());
    }

    @Override
    public int getItemCount()
    {
        return treatmentLogAdapters.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;

        TextView  tv_right_hand_dia,tv_right_hand_sys,tv_right_hand_pulse;
        TextView tv_left_hand_dia,tv_left_hand_sys,tv_left_hand_pulse;
        TextView tv_start_time;

        /*create view holder object initiliation*/
        public ViewHolder(@NonNull View itemView)
        {
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
