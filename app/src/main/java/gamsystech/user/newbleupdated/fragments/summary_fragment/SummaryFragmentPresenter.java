package gamsystech.user.newbleupdated.fragments.summary_fragment;

import android.content.Context;

import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentResponseModel;
import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AppUtil;

public class SummaryFragmentPresenter implements SummaryFragmentView.Action
{
    private Context context;
    private static final String TAG = "SummaryFragmentPresenter";
    private final SummaryFragmentView.View summaryfragmentview;

    public SummaryFragmentPresenter(Context context, SummaryFragmentView.View summaryfragmentview)
    {
        //TODO Perform Logic here
        this.context = context;
        this.summaryfragmentview = summaryfragmentview;
    }


    @Override
    public void OnBPCountTreament(TreatmentRequestModel treatmentRequestModel)
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

                }
                else

                    summaryfragmentview.OnFailed(((TreatmentResponseModel) response).getMessage());
            }


            @Override
            public void onFailure(APIError apiError)
            {
                summaryfragmentview.OnFailed(apiError.getError());
            }
        });
    }

}
