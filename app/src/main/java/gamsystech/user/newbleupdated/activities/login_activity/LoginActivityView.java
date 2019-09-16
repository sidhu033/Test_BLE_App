package gamsystech.user.newbleupdated.activities.login_activity;


public interface LoginActivityView
{
        interface View
        {
            void OnFailed(String error);

            void OnSucess(LoginResponseModel loginResponseModel);
        }
        interface Action
        {
            void onLogin(String username, String password,String IMEI);
            void  luanchInstructionactivity(Integer userID);
        }
}
