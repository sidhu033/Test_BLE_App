package gamsystech.user.newbleupdated.fragments.dashboard_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.ReadingDemo;
import gamsystech.user.newbleupdated.fragments.summary_fragment.SummaryFragment;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentResponseModel;
import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AndyUtils;
import gamsystech.user.newbleupdated.utils.AppUtil;

public class DashBoardFragmentPresenter implements  DashBoardFragmentView.Action
{
    private Context context;
    private final DashBoardFragmentView.View dasbboardfragmentview;
    private static final String TAG = "DashboardFragmentPresenter";

    /*intilize with constructor*/
    public DashBoardFragmentPresenter(Context context, DashBoardFragmentView.View dasbboardfragmentview)
    {
        //TODO Perform Logic here
        this.context = context;
        this.dasbboardfragmentview = dasbboardfragmentview;
    }

    @Override
    public void OnBpTreatmentComplete(final TreatmentRequestModel treatmentRequestModel)
    {
        if (!AppUtil.isNetworkAvailable(context))
        {
            AppUtil.showToast(context, "No internet connection");

        }

        APIHelper.getInstance().TreatmentInsert(treatmentRequestModel, new OnHttpRetrofitResponseReceived()
        {
            @Override
            public void onResponseReceived(Object response)
            {
                TreatmentResponseModel treatmentResponseModel = (TreatmentResponseModel) response;

                if(treatmentResponseModel.getIsSucess())
                {
                    AppUtil.showToast(context, treatmentResponseModel.getMessage());
                    dasbboardfragmentview.Onsucess(treatmentRequestModel);

                    android.util.Log.d("TAg",""+response);
                }
                else

                    dasbboardfragmentview.OnFailed(((TreatmentResponseModel) response).getMessage());
            }


            @Override
            public void onFailure(APIError apiError)
            {
                AppUtil.showToast(context, apiError.getError());
            }
        });

    }

    @Override
    public void launchReadingDemo(TreatmentResponseModel treatmentResponseModel, TreatmentRequestModel treatmentRequestModel)
    {


    }



}
