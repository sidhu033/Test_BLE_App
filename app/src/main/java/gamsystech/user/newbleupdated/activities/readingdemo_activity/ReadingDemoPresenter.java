package gamsystech.user.newbleupdated.activities.readingdemo_activity;

import android.content.Context;
import android.util.Log;

import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterResponseModel;
import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AppUtil;

public class ReadingDemoPresenter implements ReadingDemoView.Action {

    private final Context rdcontext;
    private final ReadingDemoView.View view;

    public ReadingDemoPresenter(Context rdcontext, ReadingDemoView.View view) {
        this.rdcontext = rdcontext;
        this.view = view;
    }


    @Override
    public void getTreatmentData(final TreatmentGetRequestModel treatmentGetRequestModel) {


        if (!AppUtil.isNetworkAvailable(rdcontext))
        {
            AppUtil.showToast(rdcontext, "No internet connection");
            return;
        }

        APIHelper.getInstance().TreatmentGet(treatmentGetRequestModel, new OnHttpRetrofitResponseReceived() {
            @Override
            public void onResponseReceived(Object response) {

                TreatmentGetResponseModel treatmentGetResponseModel = (TreatmentGetResponseModel) response;
                if(treatmentGetResponseModel.getIsSucess())
                {
                    AppUtil.showToast(rdcontext, treatmentGetResponseModel.getMessage());
                    view.onSuccess(treatmentGetResponseModel.getTreatmentDetails());
                    AppUtil.showToast(rdcontext,((TreatmentGetResponseModel) response).getMessage());
                }

            }

            @Override
            public void onFailure(APIError apiError) {
                AppUtil.showToast(rdcontext, apiError.getError());
            }
        });
    }
}
