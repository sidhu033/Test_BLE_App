package gamsystech.user.newbleupdated.activities.registration_activity;

import android.content.Context;

import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AppUtil;

public class RegisterActivityPresenter implements RegisterActivityView.Action
{
    private final Context mContext;
    private final RegisterActivityView.View mView;

   public RegisterActivityPresenter(Context mContext, RegisterActivityView.View mView)
    {
        this.mContext = mContext;
        this.mView = mView;
    }

    /*network response*/
    @Override
    public void onRegister(RegisterRequestModel addUserData)
    {
        if(!AppUtil.isNetworkAvailable(mContext))
        {
            AppUtil.showToast(mContext, "No internet connection");
            return;
        }

    }

    @Override
    public void getStateListFromServer(int countryid) {
        APIHelper.getInstance().callToServerStateList(String.valueOf(countryid), new OnHttpRetrofitResponseReceived() {
            @Override
            public void onResponseReceived(Object response) {
                CountryListResponseModel model = (CountryListResponseModel)response;

                if(model.getIsSucess())
                    mView.onStateListSuccess(model.getStatesList());
                else AppUtil.showToast(mContext,model.getMessage());
            }

            @Override
            public void onFailure(APIError apiError) {
                AppUtil.showToast(mContext,apiError.getError());
            }
        });
    }

    @Override
    public void getCityListFromServer(int cityId) {

            APIHelper.getInstance().callToServerCityList(String.valueOf(cityId), new OnHttpRetrofitResponseReceived() {
                @Override
                public void onResponseReceived(Object response) {
                    CountryListResponseModel cityListResponseModel = (CountryListResponseModel) response;

                    if(cityListResponseModel.getIsSucess())
                            mView.onCityListSucess(cityListResponseModel.getCitiesList());
                    else AppUtil.showToast(mContext,cityListResponseModel.getMessage());
                }

                @Override
                public void onFailure(APIError apiError) {
                    AppUtil.showToast(mContext,apiError.getError());
                }
            });
    }

}
