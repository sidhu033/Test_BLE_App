package gamsystech.user.newbleupdated.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLPeerUnverifiedException;

import gamsystech.user.newbleupdated.activities.login_activity.LoginRequestModel;
import gamsystech.user.newbleupdated.activities.login_activity.LoginResponseModel;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.TreatmentGetRequestModel;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.TreatmentGetResponseModel;
import gamsystech.user.newbleupdated.activities.registration_activity.CountryListResponseModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;
import gamsystech.user.newbleupdated.activities.registration_activity.RegisterResponseModel;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import gamsystech.user.newbleupdated.model.TreatmentResponseModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    private static final String TAG = APIHelper.class.getSimpleName();
    private static final String BASE_URL = "http://192.168.1.7/GamRedoxer/api/";
    private static APIHelper instance;
    private ApiInterface apiService;
    private static Retrofit retrofit;

    private static final int TIME_OUT_SECONDS = 30;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAILURE = 1;


    public static String DEVICE_TOEKN;

    /*initilize  the api instance service*/
    public static synchronized void init() {
        if (null == instance) {
            instance = new APIHelper();
            instance.initAPIService();
        }
    }

    public static APIHelper getInstance() {
        return instance;
    }


    /**
     * initialize api service
     */
    private void initAPIService() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (DEVICE_TOEKN != null && DEVICE_TOEKN.isEmpty()) {
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Log.d(TAG, "intercept: ----" + DEVICE_TOEKN);
                    Request request = original.newBuilder()
                            .header("token", DEVICE_TOEKN)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientBuilder.build())
                .build();

        apiService = retrofit.create(ApiInterface.class);
    }

    /*api error*/
    private static APIError apiErrorHandler(ResponseBody errorBody) {
        Converter<ResponseBody, APIError> errorConverter = APIHelper.retrofit.responseBodyConverter(APIError.class, new Annotation[0]);
        APIError apiError = null;
        try {
            apiError = errorConverter.convert(errorBody);
        } catch (IOException | IllegalStateException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return apiError;
    }


    /*-------------------------------------------------------------------------------------------------------------------------*/

    /**
     * Registration call_icon here to server
     *
     * @param registerRequestModel
     * @param onHttpRetrofitResponseReceived
     */
    public void registerUser(RegisterRequestModel registerRequestModel, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<RegisterResponseModel> call = apiService.registerUser(registerRequestModel);
        callRetrofit(call, onHttpRetrofitResponseReceived);
    }


    /**
     * Treatment call_icon here to server
     *
     * @param treatmentRequestModel
     * @param onHttpRetrofitResponseReceived
     */

    public void TreatmentInsert(TreatmentRequestModel treatmentRequestModel, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<TreatmentResponseModel> call = apiService.TreatmentInsert(treatmentRequestModel);
        callRetrofit(call, onHttpRetrofitResponseReceived);
    }


    public void getLoginOnServer(LoginRequestModel loginRequestModel, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<LoginResponseModel> call = apiService.getLogin(loginRequestModel);
        callRetrofit(call, onHttpRetrofitResponseReceived);

    }


    public void TreatmentGet(TreatmentGetRequestModel treatmentGetRequestModel, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<TreatmentGetResponseModel> call = apiService.TreatmentGet(treatmentGetRequestModel);
        callRetrofit(call, onHttpRetrofitResponseReceived);
    }

    public void callToServerStateList(String countryId, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<CountryListResponseModel> call = apiService.callToServerStateList(countryId);
        callRetrofit(call, onHttpRetrofitResponseReceived);
    }
    public void callToServerCityList(String stateId, OnHttpRetrofitResponseReceived onHttpRetrofitResponseReceived) {
        Call<CountryListResponseModel> call = apiService.callToServerCityList(stateId);
        callRetrofit(call, onHttpRetrofitResponseReceived);
    }


    /**
     * generic retrofit call_icon
     *
     * @param call
     * @param onHttpResponseReceived
     * @param <T>
     */
    private <T> void callRetrofit(Call<T> call, final OnHttpRetrofitResponseReceived onHttpResponseReceived) {
        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.i(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    onHttpResponseReceived.onResponseReceived(response.body());
                } else {
                    APIError apiError = apiErrorHandler(response.errorBody());
                    onHttpResponseReceived.onFailure(apiError == null ? new APIError(String.valueOf("Sorry, we currently cannot process your request. Please do try after some time.")) : apiError);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                if (t != null && t instanceof SocketTimeoutException) {
                    onHttpResponseReceived.onFailure(new APIError(String.valueOf("The request has timed out")));
                } else if (t != null && t instanceof SSLPeerUnverifiedException) {
                    onHttpResponseReceived.onFailure(new APIError("unauthorized access."));
                } else {
                    onHttpResponseReceived.onFailure(new APIError(String.valueOf("Sorry, we currently cannot process your request. Please do try after some time.")));
                }
            }
        });
    }


}
