package gamsystech.user.newbleupdated.activities.registration_activity;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gamsystech.user.newbleupdated.R;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder> {

    List<CountryListResponseModel.StatesList> treatmentDetails;
    List<CountryListResponseModel.CitiesList> citiesLists;
    Context context;
    boolean isFromState;

    /*constructor for recycle  view*/

    public StateListAdapter(RegisterActivity context, List<CountryListResponseModel.StatesList> statesList, boolean isFromState) {
        this.treatmentDetails = statesList;
        this.context = context;
        this.isFromState = isFromState;
    }

    public StateListAdapter(RegisterActivity context, boolean b, List<CountryListResponseModel.CitiesList> citiesList) {
        this.citiesLists = citiesList;
        this.context = context;
        isFromState = b;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);
        return new StateListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        if (isFromState) {
            final CountryListResponseModel.StatesList treatmentGetResponseModel = treatmentDetails.get(position);
            viewHolder.tvState.setText(treatmentGetResponseModel.getStateName());

            viewHolder.tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RegisterActivity) context).setStateId(treatmentGetResponseModel.getStateId(), treatmentGetResponseModel.getStateName(),true);
                }
            });
        }else {
            final CountryListResponseModel.CitiesList treatmentGetResponseModel = citiesLists.get(position);
            viewHolder.tvState.setText(treatmentGetResponseModel.getCityName());

            viewHolder.tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RegisterActivity) context).setStateId(treatmentGetResponseModel.getStateId(), treatmentGetResponseModel.getCityName(),false);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (isFromState ? treatmentDetails.size() : citiesLists.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvState)
        public TextView tvState;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
