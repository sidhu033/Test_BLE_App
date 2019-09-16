package gamsystech.user.newbleupdated.retrofit;


import gamsystech.user.newbleupdated.activities.login_activity.LoginRequestModel;
import gamsystech.user.newbleupdated.activities.login_activity.LoginResponseModel;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.TreatmentGetRequestModel;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.TreatmentGetResponseModel;
import gamsystech.user.newbleupdated.activities.registration_activity.CountryListResponseModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterResponseModel;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("Redoxer/RedoxerRegistration")
    Call<RegisterResponseModel> registerUser(@Body RegisterRequestModel registerMobileRequestModel);

    /*for treatment insert*/
    @Headers("Content-Type: application/json")
    @POST("HealthTreatment/InsertTreatmentInformationinfo")
    Call<TreatmentResponseModel> TreatmentInsert(@Body TreatmentRequestModel treatmentRequestModel);

    /*for login*/
    @Headers("Content-Type: application/json")
    @POST("Login/Login")
    Call<LoginResponseModel> getLogin(@Body LoginRequestModel loginRequestModel);

    @Headers("Content-Type: application/json")
    @POST("HealthTreatment/UserHealthTretmentInfo")
    Call<TreatmentGetResponseModel> TreatmentGet(@Body TreatmentGetRequestModel treatmentGetRequestModel);

    @Headers("Content-Type: application/json")
    @GET("entity/GetStatesList/{countryId}")
    Call<CountryListResponseModel> callToServerStateList(@Path("countryId") String countryId);

    @Headers("Content-Type: application/json")
    @GET("entity/GetCitiesList/{countryId}")
    Call<CountryListResponseModel> callToServerCityList(@Path("countryId") String countryId);
}





