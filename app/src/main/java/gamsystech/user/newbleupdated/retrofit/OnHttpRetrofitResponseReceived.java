package gamsystech.user.newbleupdated.retrofit;

public interface OnHttpRetrofitResponseReceived
{
    public void onResponseReceived(Object response);
    public void onFailure(APIError apiError);
}
