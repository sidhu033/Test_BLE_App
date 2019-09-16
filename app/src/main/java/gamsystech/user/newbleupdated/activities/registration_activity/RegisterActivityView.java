package gamsystech.user.newbleupdated.activities.registration_activity;


import java.util.List;


public interface RegisterActivityView
{

    interface View
    {

        void OnFailed(String error);

        void onStateListSuccess(List<CountryListResponseModel.StatesList> statesList);

        void setStateId(Integer stateId, String stateName,boolean isFromState);

        void onCityListSucess(List< CountryListResponseModel.CitiesList> citiesList);
    }

    interface Action
    {

          void onRegister(RegisterRequestModel addUserData);

         void getStateListFromServer(int countryid);
         void getCityListFromServer(int stateid);

    }
}
