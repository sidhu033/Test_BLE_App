package gamsystech.user.newbleupdated.activities.login_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import gamsystech.user.newbleupdated.DatabaseHelper.localstorage.SharedPreferenceManager;
import gamsystech.user.newbleupdated.activities.instruction_activity.InstructionActivity;
import gamsystech.user.newbleupdated.retrofit.APIError;
import gamsystech.user.newbleupdated.retrofit.APIHelper;
import gamsystech.user.newbleupdated.retrofit.OnHttpRetrofitResponseReceived;
import gamsystech.user.newbleupdated.utils.AndyUtils;


public class LoginActivityPresenter implements  LoginActivityView.Action
{
    Context context;

    LoginActivityView.View loginActivityView;
    private static final String TAG = "LoginActivityPresenter";
    public static String Token;

    //constructor to initilize presenter
    public LoginActivityPresenter(Context context, LoginActivityView loginActivityView, Resources resources)
    {
        this.context = context;
        this.loginActivityView = (LoginActivityView.View) loginActivityView;
    }


    //on login hit api
    @Override
    public void onLogin(String username, String password, String DeviceId) {
        LoginRequestModel model = new LoginRequestModel();
        model.setMobileNumber(username);
        model.setPin(password);
        model.setDeviceId(DeviceId);

        APIHelper.getInstance().getLoginOnServer(model, new OnHttpRetrofitResponseReceived() {
            @Override
            public void onResponseReceived(Object response) {

                LoginResponseModel loginResponseModel = (LoginResponseModel) response;

                if(loginResponseModel.getIsSucess())
                {
                    AndyUtils.showToastMsg(context,loginResponseModel.getMessage());
                    Log.d(TAG,"response"+loginResponseModel.getMessage());

                    loginActivityView.OnSucess(loginResponseModel);


                    APIHelper.DEVICE_TOEKN =   loginResponseModel.getToken();               //token added to header

                }
                else
                    loginActivityView.OnFailed(loginResponseModel.getMessage());
            }

            @Override
            public void onFailure(APIError apiError) {
                Log.d(TAG, "onFailure: ---"+apiError.getError());
                    loginActivityView.OnFailed(apiError.getError());
            }
        });

    }

    /**
     * launch instruction activity
     * @param
     */
    @Override
    public void luanchInstructionactivity(Integer userid) {

        Intent intent = new Intent(context, InstructionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        SharedPreferenceManager.setUserId(String.valueOf(userid));
        SharedPreferenceManager.setUserLogged(true);
        context.startActivity(intent);
        ((Activity) context).finish();

    }
}
