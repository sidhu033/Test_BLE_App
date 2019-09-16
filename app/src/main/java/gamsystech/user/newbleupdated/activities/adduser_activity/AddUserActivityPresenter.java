package gamsystech.user.newbleupdated.activities.adduser_activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import gamsystech.user.newbleupdated.activities.instruction_activity.InstructionActivity;

import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterResponseModel;
import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AppUtil;
import gamsystech.user.newbleupdated.utils.Constants;

public class AddUserActivityPresenter implements AddUserActivityView.Action
{
    private final Context mContext;
    AddUserActivityView.View mView;
    private static final String TAG = "ADDUserActivityPresenter";

    public AddUserActivityPresenter(Context mContext, AddUserActivityView.View mView)
    {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void onRegister(final RegisterRequestModel addUserData)
    {
        if (!AppUtil.isNetworkAvailable(mContext))
        {
            AppUtil.showToast(mContext, "No internet connection");
            return;
        }

        APIHelper.getInstance().registerUser(addUserData, new OnHttpRetrofitResponseReceived()
        {
            @Override
            public void onResponseReceived(Object response)
            {
                RegisterResponseModel responseModel = (RegisterResponseModel) response;

                if(responseModel.getIsSucess())
                {
                    AppUtil.showToast(mContext, responseModel.getMessage());
                    Log.d("TAG",((RegisterResponseModel) response).getMessage());
                    lunchinstructionActivity(addUserData.getUserName());
                }
                else

                     mView.OnFailed(responseModel.getMessage());
                    AppUtil.showToast(mContext,"cannot pass");

            }

            @Override
            public void onFailure(APIError apiError)
            {
                mView.OnFailed(apiError.getError());
            }
        });
    }

    /*get state list in dialog box*/

    @Override
    public void getStateList(String countryId) {


    }

    /*get city list in dialog box*/
    @Override
    public void getCityList(Integer stateId) {

    }

    private void lunchinstructionActivity(String userName)
    {
        Intent intent = new Intent(mContext, InstructionActivity.class);
        intent.putExtra(Constants.KEY_USER_NAME, userName);
        mContext.startActivity(intent);
    }
}
