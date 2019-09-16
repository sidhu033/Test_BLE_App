package gamsystech.user.newbleupdated.activities.adduser_activity;

import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;

public class AddUserActivityView
{
    public interface View
    {

        void OnFailed(String error);

        void OnAddUserSucess();

        void getStateListFromServer(String countryId);
        void getCityListFromServer(Integer stateId);

    }

    public interface Action
    {

        void onRegister(RegisterRequestModel addUserData);
        void getStateList(String countryId);
        void getCityList(Integer stateId);

    }

}
